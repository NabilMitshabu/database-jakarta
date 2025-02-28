/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jacartavisa.business;


import entities.Visite;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author HP
 */


@Stateless
@LocalBean
public class VisiteEntrepriseBean {
    
    @PersistenceContext
    private EntityManager entityManager;
    // Méthode pour ajouter une nouvelle visite
    public void ajouterVisite(Visite visite) {
        entityManager.persist(visite);
    }

    // Méthode pour obtenir toutes les visites
    public List<Visite> obtenirVisites() {
        return entityManager.createQuery("SELECT v FROM Visite v", Visite.class).getResultList();
    }

    // Méthode pour obtenir une visite par ID
    // Méthode pour obtenir les visites d'un utilisateur par ID
   public List<Visite> obtenirVisitesParUtilisateur(Long idUtilisateur) {
    return entityManager.createQuery(
        "SELECT v FROM Visite v JOIN FETCH v.lieu WHERE v.idUtilisateur = :idUtilisateur", Visite.class)
        .setParameter("idUtilisateur", idUtilisateur)
        .getResultList();
    }
     
}