/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jacartavisa.Bean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;

/**
 *
 * @author Orman
 */
@Named(value = "navigationController")
@RequestScoped
public class NavigationBean {
    
        public void redirection(String url){
            try{ 
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(url);
            }catch (IOException e){
                
                e.printStackTrace();
            }
        }
        public void ajouterLieu(){
            redirection("pages/lieu.xhtml");
    }
    public void voirApropos(){
            redirection("pages/a_propos.xhtml");
    }
    
    public void visiterLieux(){
         redirection("pages/guide.xhtml");
    
    }
}
