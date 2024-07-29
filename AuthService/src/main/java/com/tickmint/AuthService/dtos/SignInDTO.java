package com.tickmint.AuthService.dtos;

public class SignInDTO {
    private String email;
    private Long phone;
    private String password;

    public SignInDTO() {
    }

    public SignInDTO(String email, String password, Long phone) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
