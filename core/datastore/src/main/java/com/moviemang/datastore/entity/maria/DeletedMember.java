package com.moviemang.datastore.entity.maria;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity(name = "deleted_member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeletedMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deleted_member_id")
    private Long deletedMemberId;

    @Email(message = "이메일을 올바르게 입력하세요.")
    @Column(name = "member_email")
    private String memberEmail; // 이메일

    @CreatedDate
    private LocalDateTime regDate;

    @Builder
    public DeletedMember(Long deletedMemberId, String memberEmail, LocalDateTime regDate) {
        this.deletedMemberId = deletedMemberId;
        this.memberEmail = memberEmail;
        this.regDate = regDate;
    }

}
