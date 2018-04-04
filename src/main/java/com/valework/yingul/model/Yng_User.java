package com.valework.yingul.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valework.yingul.model.security.Authority;
import com.valework.yingul.model.security.Yng_UserRole;

@Entity
public class Yng_User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String phone;
    private String phone2;
    private String webSite;
	private String documentType="";
	private String 	documentNumber="";
	
	@Value("${some.key:sampleBanner.jpg}")
	private java.lang.String profileBanner;
	@Value("${some.key:profile.jpg}")
	private java.lang.String profilePhoto;
	@Value("${some.key:https://www.youtube.com/embed/1AV37mvCHQo}")
	private java.lang.String profileVideo;
	
    private boolean enabled=true;

    @OneToOne
	private Yng_Ubication yng_Ubication;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Yng_UserRole> userRoles = new HashSet<>();

    public Set<Yng_UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Yng_UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	
	public Yng_Ubication getYng_Ubication() {
		return yng_Ubication;
	}

	public void setYng_Ubication(Yng_Ubication yng_Ubication) {
		this.yng_Ubication = yng_Ubication;
	}
	


	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Yng_User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public java.lang.String getProfileBanner() {
		return profileBanner;
	}

	public void setProfileBanner(java.lang.String profileBanner) {
		this.profileBanner = profileBanner;
	}

	public java.lang.String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(java.lang.String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public java.lang.String getProfileVideo() {
		return profileVideo;
	}

	public void setProfileVideo(java.lang.String profileVideo) {
		this.profileVideo = profileVideo;
	}


}