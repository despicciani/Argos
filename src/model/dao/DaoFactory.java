package model.dao;

import db.DB;
import model.dao.impl.ClasseDaoJDBC;
import model.dao.impl.ItemDaoJDBC;
import model.dao.impl.PersonagemDaoJDBC;
import model.dao.impl.RacaDaoJDBC;

public class DaoFactory {
	
	public static PersonagemDao createPersonagemDao() {
		RacaDao racaD = createRacaDao(); 
        ClasseDao classeD = createClasseDao();
        ItemDao itemD = createItemDao();
		return new PersonagemDaoJDBC(DB.getConnection(), racaD, classeD, itemD);
	}
	
	public static RacaDao createRacaDao() {
		return new RacaDaoJDBC(DB.getConnection());
	}
	
	public static ClasseDao createClasseDao() {
		return new ClasseDaoJDBC(DB.getConnection());
	}
	
	public static ItemDao createItemDao() {
		return new ItemDaoJDBC(DB.getConnection());
	}
}
