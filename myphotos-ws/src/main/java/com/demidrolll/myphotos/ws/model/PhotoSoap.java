package com.demidrolll.myphotos.ws.model;

import com.demidrolll.myphotos.common.annotation.converter.ConvertAsUrl;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "photo")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotoSoap {

    @XmlAttribute(required = true)
    private Long id;

    @ConvertAsUrl
    private String smallUrl;

    private long views;

    private long downloads;

    private ProfileSoap profile;
}
