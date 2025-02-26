package com.mycompany.jacartavisa.business;

import entities.Utilisateur;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.servlet.http.HttpSession;

@Named("a_proposBean")
@SessionScoped
public class A_proposBean implements Serializable {
    
    private final String username = "";
    private final String email = "";
    private String newPassword;
    private String description;
   
     @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    @Inject
    private SessionManager sessionManager;

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public Utilisateur getUserFromSession() {
    String email = getUserInfoEmail();
    System.out.println("Email récupéré de la session: " + email);
    
    if (email == null || email.equals("Aucune information utilisateur disponible.")) {
        return null;
    }
    
    Utilisateur user = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
    if (user != null) {
        System.out.println("Utilisateur trouvé: " + user.getUsername());
    } else {
        System.out.println("Aucun utilisateur trouvé avec cet email.");
    }
    
    return user;
}

    
  
    
  public String getUserInfoEmail() {
    String sessionEmail = sessionManager.getValueFromSession("user");
    
    return sessionEmail != null ? sessionEmail : "Aucune information utilisateur disponible.";
}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  

 
 // Méthode pour récupérer le username de l'utilisateur connecté
    public String getUserInfoUsername() {
        Utilisateur user = getUserFromSession();
        return user != null ? user.getUsername() : "Aucun utilisateur trouvé.";
    }
    
    

  public String updatePassword() {
    Utilisateur user = getUserFromSession();

    if (user != null) {
        try {
            String currentPassword = null;
            utilisateurEntrepriseBean.updatePassword(user.getUsername(), currentPassword, newPassword);
            clearPasswordFields(); // Réinitialiser les champs après mise à jour
            return "Mot de passe modifié avec succès!"; // Message de confirmation
        } catch (RuntimeException e) {
            return e.getMessage(); // Retourner le message d'erreur
        }
    }
    return "Erreur lors de la mise à jour du mot de passe.";
}
    
    
   
   


  
    
    // Méthode pour modifier le profil
public String editProfile1() {
    // Logique pour permettre la modification des informations utilisateur
    // Exemple : userService.updateProfile(username, email);
    return "modifier_profil?faces-redirect=true"; // Redirection vers la page du profil
}


    public String editProfile() {
    Utilisateur user = getUserFromSession();

    if (user != null) {
        try {
            // Récupérer les nouvelles valeurs depuis les propriétés du bean
            String username = getUserInfoUsername(); // Récupérer le nom d'utilisateur
            String email = getUserInfoEmail();       // Récupérer l'email
            String description = getDescription();    // Récupérer la description

            // Appeler la méthode pour mettre à jour le profil de l'utilisateur
            utilisateurEntrepriseBean.updateProfile(username, email, description);

            return "Profil modifié avec succès!"; // Message de confirmation
        } catch (RuntimeException e) {
            return e.getMessage(); // Retourner le message d'erreur
        }
    }
    return "Erreur lors de la mise à jour du profil.";
}

    // Méthode pour déconnecter l'utilisateur
    public void logout() {
       sessionManager.invalidateSession();
        
    }

    private void clearPasswordFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
