package com.fooddelivery.delivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tự tăng (1,2,3)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleName getName() {
		return name;
	}

	public void setName(RoleName name) {
		this.name = name;
	}
    
    
}
