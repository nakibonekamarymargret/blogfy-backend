package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.dto.PostRequest;
import com.MASOWAC.blogfy.dto.PostResponse;
import com.MASOWAC.blogfy.enums.PostStatus;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Tag;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.repositories.TagRepository;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import com.cloudinary.Cloudinary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UsersRepository userRepository;
    private final Cloudinary cloudinary;


    public PostService(PostRepository postRepository, TagRepository tagRepository, UsersRepository userRepository, Cloudinary cloudinary) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    //    Get currently logged user
    private Users getCurrentUser(Principal principal) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //    public PostResponse createPost(PostRequest request, String name) throws IOException {
//        Users user = getCurrentUser();
//
//        Post post = new Post();
//        post.setTitle(request.getTitle());
//        post.setContent(request.getContent());
//        post.setAuthor(user);
//
//        if (request.getCoverImageUrl() != null) {
//            Map uploadResult = cloudinary.uploader().upload(request.getCoverImageUrl().getBytes(), Map.of());
//            post.setCoverImageUrl(uploadResult.get("secure_url").toString());
//        }
//
//        if ("publish".equalsIgnoreCase(request.getAction())) {
//            post.setStatus(PostStatus.published);
//        } else {
//            post.setStatus(PostStatus.draft);
//        }
//
//        return new PostResponse(postRepository.save(post));
//    }
    public Post createPost(String title, String content, String coverImageUrl, Principal principal) throws IOException {
        Users user = getCurrentUser(principal);
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCoverImageUrl(coverImageUrl); // Set the URL directly
        post.setAuthor(user);
        post.setStatus(PostStatus.published);
        post.setPublishedAt(new Date());
        post.setUpdatedAt(new Date());
        return postRepository.save(post);
    }

    public PostResponse updatePost(Long id, PostRequest request, String username) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("Unauthorized to update this post");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        post.setTags(parseTags(request.getTagNames()));
        post.setPostImages(request.getContentImageUrls());

        return new PostResponse(postRepository.save(post));
    }

    public void deletePost(Long id, String username) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("Unauthorized to delete this post");
        }
        post.setStatus(PostStatus.deleted);
        postRepository.save(post);
    }

    public PostResponse getPostById(Long id) {
        return new PostResponse(postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found")));
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : posts) {
            responses.add(new PostResponse(post));
        }
        return responses;
    }

    public List<PostResponse> getPostsByTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : tag.getPosts()) {
            responses.add(new PostResponse(post));
        }
        return responses;
    }

    private Set<Tag> parseTags(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name.trim().toLowerCase())
                    .orElseGet(() -> tagRepository.save(new Tag(name.trim().toLowerCase())));
            tags.add(tag);
        }
        return tags;
    }
}
