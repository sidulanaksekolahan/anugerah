package com.elkamil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] data;

    @Column(name = "display_image_uri")
    private String displayImageUri;

    @Column(name = "download_image_uri")
    private String downloadImageUri;

    public Product(String name, String type, byte[] data) {
        this.imageName = name;
        this.imageType = type;
        this.data = data;
    }
}
