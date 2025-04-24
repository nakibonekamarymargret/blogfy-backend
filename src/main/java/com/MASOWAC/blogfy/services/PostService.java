package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.enums.PostStatus;
import com.MASOWAC.blogfy.exceptions.PostNotFoundException;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.PostLikes;
import com.MASOWAC.blogfy.models.Tag;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.PostLikesRepository;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.repositories.TagRepository;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UsersRepository userRepository;
    private final PostLikesRepository postLikesRepo;

    public PostService(PostRepository postRepository, TagRepository tagRepository,
                       UsersRepository userRepository, PostLikesRepository postLikesRepo) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.postLikesRepo = postLikesRepo;
    }

    public Post createPost(String title, String content, String coverImageUrl, Principal principal) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCoverImageUrl(coverImageUrl); // Directly set the URL

        String loginName = principal.getName();
        Users author;

        if (loginName.contains("@")) {
            author = userRepository.findByEmail(loginName)
                    .orElseThrow(() -> new RuntimeException("Author not found with email: " + loginName));
        } else {
            author = userRepository.findByUsername(loginName)
                    .orElseThrow(() -> new RuntimeException("Author not found with username: " + loginName));
        }

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

    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedAt"));
    }

    public List<Post> getPostsByTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
        return new ArrayList<>(tag.getPosts());
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    // Like/Unlike Logic
    public void likeUnlikePost(Long postId, String usernameOrEmail) {
        // Fetch post by postId
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Find the user by username or email
        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user has already liked the post
        Optional<PostLikes> existingLike = postLikesRepo.findByPostAndLikedBy(post, user);

        if (existingLike.isPresent()) {
            // Unlike
            postLikesRepo.delete(existingLike.get());
        } else {
            // Like
            PostLikes like = new PostLikes();
            like.setPost(post);
            like.setLikedBy(user);
            like.setLikedAt(new Date());
            postLikesRepo.save(like);
        }
    }


    public boolean hasUserLikedPost(Long postId, String username) {
        return postLikesRepo.findByPostIdAndLikedBy_Username(postId, username).isPresent();
    }

    public void removeLikeUnlikeService(Long id) {
        postLikesRepo.deleteById(id);
    }

    public Integer noOfLikes(Long postId) {
        AtomicReference<Integer> count = new AtomicReference<>(0);
        postLikesRepo.findAllByPostId(postId)
                .forEach(postLikes -> {
                    if (postLikes.getLikedBy() != null) {
                        count.updateAndGet(v -> v + 1);
                    }
                });
        return count.get();
    }

    public Integer noOfUnLikes(Long postId) {
        AtomicReference<Integer> count = new AtomicReference<>(0);
        postLikesRepo.findAllByPostId(postId)
                .forEach(blogsLikes -> {
                    if (blogsLikes.getUnlikedBy() != null) {
                        count.updateAndGet(v -> v + 1);
                    }
                });
        return count.get();
    }

    public List<PostLikes> allPostLikes() {
        return postLikesRepo.findAll();
    }
}
