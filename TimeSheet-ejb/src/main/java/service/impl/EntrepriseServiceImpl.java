package service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Departement;
import entities.Entreprise;
import service.interf.EntrepriseServiceRemote;

@Stateless
public class EntrepriseServiceImpl implements EntrepriseServiceRemote {
	@PersistenceContext(unitName = "imputation-ejb")
	EntityManager em;
	public int ajouterEntreprise(Entreprise entreprise) {
		em.persist(entreprise);
		return entreprise.getId();}
	@Override
	public int ajouterDepartement(Departement dep) {
		em.persist(dep);
		return dep.getId();	}
	@Override
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		Entreprise entrepriseManagedEntity = em.find(Entreprise.class, entrepriseId);
		Departement depManagedEntity = em.find(Departement.class, depId);
		depManagedEntity.setEntreprise(entrepriseManagedEntity);
	}
	@Override
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = em.find(Entreprise.class, entrepriseId);
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
		depNames.add(dep.getName());}
		return null;
	}
	@Override
	public void deleteEntrepriseById(int entrepriseId) {
		em.remove(em.find(Entreprise.class, entrepriseId));
		
	}
	@Override
	public void deleteDepartementById(int depId) {
		em.remove(em.find(Departement.class, depId));
		
	}
	@Override
	public Entreprise getEntrepriseById(int entrepriseId) {
		return em.find(Entreprise.class, entrepriseId);
		
	}
}
