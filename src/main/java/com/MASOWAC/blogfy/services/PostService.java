package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.enums.PostStatus;
import com.MASOWAC.blogfy.exceptions.PostNotFoundException;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Tag;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.repositories.TagRepository;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

// PostService.java
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

    public Post createPost(String title, String content, MultipartFile coverImageFile, Principal principal) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        if (coverImageFile != null && !coverImageFile.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(coverImageFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url").toString();
            post.setCoverImageUrl(imageUrl);
        }

        Users author = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        post.setAuthor(author);
        post.setPublishedAt(new Date());
        post.setStatus(PostStatus.published);

        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post requestBody, String username) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("Unauthorized to update this post");
        }

        post.setTitle(requestBody.getTitle());
        post.setContent(requestBody.getContent());
        post.setStatus(requestBody.getStatus());
        post.setPostImages(requestBody.getPostImages());
        post.setTags(requestBody.getTags());
        post.setUpdatedAt(new Date());

        return postRepository.save(post);
    }

    public void deletePost(Long id, String username) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new AccessDeniedException("Unauthorized to delete this post");
        }
        post.setStatus(PostStatus.deleted);
        postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findByIdWithAuthor(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    //    public Page<Post> getAllPosts(Pageable pageable){
//        return postRepository.findAllByOrderByPublishedAtDesc( pageable);
//    }
    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedAt"));

    }

    public List<Post> getPostsByTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
        return new ArrayList<>(tag.getPosts());
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        return postRepository.findByAuthorId(authorId); // Assuming you have a method in your repository
    }

}
