package service.interf;

import java.util.List;

import javax.ejb.Remote;

import entities.Entreprise;
import entities.Departement;

@Remote
public interface EntrepriseServiceRemote {
	public int ajouterEntreprise(Entreprise entreprise);
	public int ajouterDepartement(Departement dep);
	void affecterDepartementAEntreprise(int depId, int entrepriseId);
	List<String> getAllDepartementsNamesByEntreprise(int entrepriseId);
    public void deleteEntrepriseById(int entrepriseId);
    public void deleteDepartementById(int depId);
    public Entreprise getEntrepriseById(int entrepriseId);

}
