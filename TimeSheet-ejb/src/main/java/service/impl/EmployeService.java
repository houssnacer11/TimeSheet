package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import entities.Contrat;
import entities.Employe;
import entities.Entreprise;
import entities.Mission;
import entities.Timesheet;
import entities.Departement;
import service.interf.EmployeServiceRemote;

public class EmployeService implements EmployeServiceRemote {
	@PersistenceContext(unitName = "imputation-ejb")
	EntityManager em;
	public int ajouterEmploye(Employe employe){
		em.persist(employe);
		return employe.getId();
	}
	@Override
	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = em.find(Employe.class, employeId);
		employe.setEmail(email);
		
	}
	@Override
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = em.find(Departement.class, depId);
		Employe employeManagedEntity = em.find(Employe.class, employeId);
		if(depManagedEntity.getEmployes() == null){ List<Employe> employes = new ArrayList<>();
		employes.add(employeManagedEntity);
		depManagedEntity.setEmployes(employes);
		}else {depManagedEntity.getEmployes().add(employeManagedEntity);}
		
	}
	@Override
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Departement dep = em.find(Departement.class, depId);
		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
		if(dep.getEmployes().get(index).getId() == employeId){ dep.getEmployes().remove(index);}}
		
	}
	@Override
	public int ajouterContrat(Contrat contrat) {
		em.persist(contrat);
		return contrat.getReference();
	}
	@Override
	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = em.find(Contrat.class, contratId);
		Employe employeManagedEntity = em.find(Employe.class, employeId);
		contratManagedEntity.setEmploye(employeManagedEntity);
		em.merge(contratManagedEntity);
		
	}
	@Override
	public String getEmployePrenomById(int employeId) {
		Employe employe = em.find(Employe.class, employeId);
		return employe.getPrenom();
	}
	@Override
	public void deleteEmployeById(int employeId) {
		Employe employe = em.find(Employe.class, employeId);
		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
		dep.getEmployes().remove(employe);}
		
	}
	@Override
	public void deleteContratById(int contratId) {
		em.remove(em.find(Contrat.class, contratId));
		
	}
	@Override
	public long getNombreEmployeJPQL() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<String> getAllEmployeNamesJPQL() {
		List<Employe> employes = em.createQuery("select e from Employe e",
				Employe.class).getResultList();
				List<String> employeNames = new ArrayList<>();
				for(Employe employe : employes){
				employeNames.add(employe.getNom());}
				return employeNames;
	}
	@Override
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		Query query = em.createQuery("update Employe e set email=:email where e.id=:employeId");
		query.setParameter("email", email);
		query.setParameter("employeId", employeId);
		int modified = query.executeUpdate();
		if(modified == 1){
		System.out.println("successfully updated");
		}else{
		System.out.println("failed to update");}
		
	}
	@Override
	public void deleteAllContratJPQL() {
		int modified = em.createQuery("delete from Contrat").executeUpdate();
		if(modified > 1){
		System.out.println("successfully deleted");
		}else{
		System.out.println("failed to delete");
		}
		
	}
	@Override
	public float getSalaireByEmployeIdJPQL(int employeId) {
		TypedQuery<Float> query = em.createQuery(
				"select c.salaire from Contrat c join c.employe e where e.id=:employeId",
				Float.class);
				query.setParameter("employeId", employeId);
				return query.getSingleResult();
	}
	@Override
	public Double getSalaireMoyenByDepartementId(int departementId) {
		TypedQuery<Double> query = em.createQuery("Select "
				+ "DISTINCT AVG(cont.salaire) from Contrat cont "
				+ "join cont.employe emp "
				+ "join emp.departements deps "
				+ "where deps.id=:depId", Double.class);
				query.setParameter("depId", departementId);
				return query.getSingleResult();
	}
	@Override
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		TypedQuery<Timesheet> query = em.createQuery(
				"Select t from Timesheet t "
				+ "where t.mission=:mis and "
				+ "t.employe=:emp and "
				+ "t.timesheetPK.dateDebut>=:dateD and "
				+ "t.timesheetPK.dateFin<=:dateF", Timesheet.class);
		query.setParameter("mis", mission);
		query.setParameter("emp", employe);
		query.setParameter("dateD", dateDebut , TemporalType.DATE);
		query.setParameter("dateF", dateFin, TemporalType.DATE);
		return query.getResultList();
	}
	@Override
	public Employe getEmployeByEmailAndPassword(String login, String password) {
		TypedQuery<Employe> query = em.createQuery("select e from Employe e WHERE e.email=:email and e.password=:password ", Employe.class);
				query.setParameter("email", login);
				query.setParameter("password", password);
				Employe employe = null;
				try {
					employe = query.getSingleResult();
					}
					catch (Exception e) {
					System.out.println("Erreur : " + e);
					}
		return employe;
	}
	@Override
	public List<Employe> getAllEmployes() {
		List<Employe> emp = em.createQuery("Select e from Employe e", Employe.class).getResultList();
				return emp;
	}
}
