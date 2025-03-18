package com.example.be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "saler")
public class Saler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long salesTarget;
    private Double commissionRate;

    @JsonBackReference("userSalerReference")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference("roleSalerReference")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @JsonBackReference("orderSalerReference")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OderProduct orderProduct;

    public Saler() {
    }

    // Getters và Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(Long salesTarget) {
        this.salesTarget = salesTarget;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public OderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    @Override
    public String toString() {
        return "Saler{" +
                "id=" + id +
                ", salesTarget=" + salesTarget +
                ", commissionRate=" + commissionRate +
                ", user=" + user +
                ", role=" + role +
                ", orderProduct=" + orderProduct +
                '}';
    }
}
