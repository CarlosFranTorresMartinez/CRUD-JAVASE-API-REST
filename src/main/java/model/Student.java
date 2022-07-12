package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {

    private String _id;
    private String picture;
    private String name;
    private String dni;
    private String phone;
    private String career;
    private String email;
    private String github;
    private String linkedin;
    private String status;
}
