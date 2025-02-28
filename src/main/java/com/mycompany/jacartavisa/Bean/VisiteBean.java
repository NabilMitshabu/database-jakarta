/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jacartavisa.Bean;



import com.mycompany.jacartavisa.business.SessionManager;
import com.mycompany.jacartavisa.business.UtilisateurEntrepriseBean;
import com.mycompany.jacartavisa.business.VisiteEntrepriseBean;
import entities.Utilisateur;
import entities.Visite;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jenovic Mwambay
 */
@SessionScoped
@Named(value = "formulaireVisiteBean")
public class VisiteBean implements Serializable {
    @EJB
    private VisiteEntrepriseBean visiteEntrepriseBean; // Injection de l'EJB pour gérer les visites

    private Long idUtilisateur;
    private Long idLieu;
    private LocalDateTime dateVisite;
    private int tempsPasse;
    private String observations;
    private double depenses;
    private boolean afficherFormulaireVisite;
    private List<Visite> visitesUtilisateur; // Liste pour stocker les visites de l'utilisateur
    private String nom;
    
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    @Inject
    private SessionManager sessionManager;
    
    // Méthode pour charger les visites lors de la connexion
     public void chargerVisites() {
         
       String mail=sessionManager.getValueFromSession("user");
         Utilisateur ut=utilisateurEntrepriseBean.trouverUtilisateurParEmail(mail);
         idUtilisateur=ut.getId();
         nom = ut.getUsername();
    visitesUtilisateur = visiteEntrepriseBean.obtenirVisitesParUtilisateur(idUtilisateur);
}
   
    public List<Visite> getVisitesUtilisateur() {
        chargerVisites();
        return visitesUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
   
    
    

    @PostConstruct
    public void init() {
        dateVisite = LocalDateTime.now(); // Initialisation avec la date actuelle
    }

    public void afficherFormulaireVisite() {
        afficherFormulaireVisite = true;
    }

    public void sauvegarderVisite() {
        try {
            Visite visite = new Visite();
            visite.setIdUtilisateur(idUtilisateur.intValue()); // Conversion explicite
            visite.setIdLieu(1); // Conversion explicite
            visite.setDateVisite(dateVisite);
            visite.setTempsPasse(tempsPasse);
            visite.setObservations(observations);
            visite.setDepenses(depenses);
            
            visiteEntrepriseBean.ajouterVisite(visite); // Ajout dans la base de données

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Visite enregistrée avec succès", null));
            afficherFormulaireVisite = false;
            resetForm();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement" + e, null));
            e.printStackTrace();
        }
    }

    private void resetForm() {
        idLieu = null;
        dateVisite = LocalDateTime.now();
        tempsPasse = 0;
        observations = "";
        depenses = 0.0;
    }

    // Getters et Setters
    public LocalDateTime getDateVisite() { return dateVisite; }
    public void setDateVisite(LocalDateTime dateVisite) { this.dateVisite = dateVisite; }
    public double getDepenses() { return depenses; }
    public void setDepenses(double depenses) { this.depenses = depenses; }
    public Long getIdLieu() { return idLieu; }
    public void setIdLieu(Long idLieu) { this.idLieu = idLieu; }
    public int getTempsPasse() { return tempsPasse; }
    public void setTempsPasse(int tempsPasse) { this.tempsPasse = tempsPasse; }
    public Long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public void setAfficherFormulaireVisite(boolean afficherFormulaireVisite) { this.afficherFormulaireVisite = afficherFormulaireVisite; }
    public void setObservations(String observations) { this.observations = observations; }
    public String getObservations() { return observations; }

    public boolean isAfficherFormulaireVisite() {
        return afficherFormulaireVisite;
    }

    public VisiteEntrepriseBean getVisiteEntrepriseBean() {
        return visiteEntrepriseBean;
    }

    public void setVisiteEntrepriseBean(VisiteEntrepriseBean visiteEntrepriseBean) {
        this.visiteEntrepriseBean = visiteEntrepriseBean;
    }
    
}