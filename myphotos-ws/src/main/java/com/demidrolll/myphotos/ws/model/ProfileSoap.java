package com.demidrolll.myphotos.ws.model;

import com.demidrolll.myphotos.common.annotation.converter.ConvertAsUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "profile")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProfileSoap {

    @XmlAttribute(required = true)
    private Long id;

    private String uid;

    private String firstName;

    private String lastName;

    @ConvertAsUrl
    private String avatarUrl;

    private String jobTitle;

    private String location;

    private int photoCount;

    @XmlElementWrapper(name = "photos")
    @XmlElement(name = "photo")
    private List<ProfilePhotoSoap> photos;
}
