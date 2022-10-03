import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.devdojo.examgenerator.persistence.dao.LoginDAO;
import br.com.devdojo.examgenerator.persistence.dao.ProfessorDAO;
import br.com.devdojo.examgenerator.persistence.model.support.Token;

@Named
@ViewScoped
public class IndexBean implements Serializable {

	private static final long serialVersionUID = -6365731867766634669L;

	private String message = "Funfa!";
	private final LoginDAO loginDAO;
	private final ProfessorDAO professorDAO;
	
	@Inject
	public IndexBean(LoginDAO loginDAO, ProfessorDAO professorDAO) {
		super();
		this.loginDAO = loginDAO;
		this.professorDAO = professorDAO;
	}
	
	public void login() {
		Token token = loginDAO.loginReturningToken("thiago", "devdojo");
		System.out.println(token);
	}
	
	public void checkProfessor() {
		professorDAO.getProfessorById(1L);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

};