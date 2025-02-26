/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package com.mycompany.jacartavisa.business;


import entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    
    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        
        em.persist(utilisateur);
    }
    

    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }
    
    public Utilisateur authentifier(String email, String password){
        Utilisateur user = trouverUtilisateurParEmail(email);
        
        if(user != null && verifierMotDePasse(password, user.getPassword())){
            return user;
        }
        
        return null;
    }
    
    public Utilisateur TrouverUtilisateurParUsername(String username){
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username", Utilisateur.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    // Méthode pour vérifier un mot de passe 
    public boolean verifierMotDePasse(String password, String hashedPassword)
    { return BCrypt.checkpw(password, hashedPassword); }
    
    
 @Transactional
public void updateProfile(String username, String email, String description) {
    Utilisateur utilisateur = TrouverUtilisateurParUsername(username);
    
    if (utilisateur != null) {
        utilisateur.setEmail(email); // Mettre à jour l'email
        utilisateur.setDescription(description); // Mettre à jour la description
        em.merge(utilisateur); // Enregistrer les changements dans la base de données
    } else {
        throw new RuntimeException("Utilisateur non trouvé pour le nom d'utilisateur : " + username);
    }
}
    
    
    @Transactional
    public void updatePassword(String username, String currentPassword, String newPassword) {
    // Trouver l'utilisateur par son nom d'utilisateur
        Utilisateur utilisateur = TrouverUtilisateurParUsername(username);

        if (utilisateur != null) {
            // Vérifier si le mot de passe actuel est correct
            if (verifierMotDePasse(currentPassword, utilisateur.getPassword())) {
                // Hacher le nouveau mot de passe
                String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                utilisateur.setPassword(hashedNewPassword);
                em.merge(utilisateur); // Mettre à jour l'utilisateur dans la base de données
            } else {
                throw new RuntimeException("Le mot de passe actuel est incorrect."); // Gérer l'erreur
            }
        } else {
            throw new RuntimeException("Utilisateur non trouvé."); // Gérer l'erreur
        }
    }


}

