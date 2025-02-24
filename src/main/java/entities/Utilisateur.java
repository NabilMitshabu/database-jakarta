/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name="utilisateur")
public class Utilisateur {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      
      @Column(nullable = false, unique = true, length =50
      )
      private String username;
      
      @Column(nullable = false, unique = true, length =100
      )
      private String email;
      
      @Column(nullable = false, length =255
      )
      private String password;
      
      private String description;

    public Utilisateur() {
    }

    public Utilisateur(String username, String email, String password, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
    }
      
      
}
