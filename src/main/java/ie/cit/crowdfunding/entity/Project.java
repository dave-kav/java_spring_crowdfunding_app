package ie.cit.crowdfunding.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

/**
 * 
 * @author Dave Kavanagh	
 * @author Thomas O'Halloran
 * @author Darren Smith
 *
 */
@Entity
@Table(name="projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	@Length(min=3)
	private String name;
	@NotNull
	@Length(min=15)
	private String description;
	@URL
	private String image;
	@Min(1)
	private float goalAmt;
	/**
	 * int value representing number of days remaining,
	 * easier than messing around with dates  
	 */
	@Min(0)
	private int timeLimit;
	private boolean active;

	// list of pledges made to this project
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	@JoinTable(name="project_pledges",
		joinColumns={@JoinColumn(name="project_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="pledge_id", referencedColumnName="id")})
	private List<Pledge> pledges;
	
	@ManyToMany(mappedBy="projects", cascade = {CascadeType.PERSIST})
	private List<User> users;
	
	public Project () {
		users = new ArrayList<User>();
		pledges = new ArrayList<Pledge>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public float getGoalAmt() {
		return goalAmt;
	}
	
	public void setGoalAmt(float goalAmt) {
		this.goalAmt = goalAmt;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public List<Pledge> getPledges() {
		return pledges;
	}

	public void setPledges(List<Pledge> pledges) {
		this.pledges = pledges;
	}

	public User getUser() {
		return users.get(0);
	}

	public void setUser(User user) {
		users.add(user);
	}
	
	public float getTotal() {
		float total = 0;
		for (Pledge p: pledges) {
			total += p.getAmount();
		}
		return total;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean pledgesEmpty() {
		if(pledges.isEmpty()){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		String out = "project [id=" + id + ", name=" + name + ", description=" + description
				+ ", goal amount=" + goalAmt + "]";
		return out;
	}

}
