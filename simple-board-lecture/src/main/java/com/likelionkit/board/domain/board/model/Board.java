package com.likelionkit.board.domain.board.model;

import com.likelionkit.board.domain.board.dto.request.BoardPostRequest;
import com.likelionkit.board.domain.comment.model.Comment;
import com.likelionkit.board.domain.user.model.User;
import com.likelionkit.board.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // fk 이름을 설정
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addUser(User user) {
        this.user = user;
        user.getBoards().add(this); // 양방향 관계 설정
    }

    public void update(BoardPostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
