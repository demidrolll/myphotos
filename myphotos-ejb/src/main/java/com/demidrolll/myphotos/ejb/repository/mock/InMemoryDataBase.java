package com.demidrolll.myphotos.ejb.repository.mock;

import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InMemoryDataBase {

    public static final Profile PROFILE;

    public static final List<Photo> PHOTOS;

    static {
        PROFILE = createProfile();
        PHOTOS = createPhotos(PROFILE);
    }

    private static List<Photo> createPhotos(Profile profile) {
        Random random = new Random();
        List<Photo> photos = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Photo photo = new Photo();
            photo.setProfile(profile);
            String imageUrl = String.format("https://devstudy-net.github.io/myphotos-com-test-images/%s.jpg", i % 6 + 1);
            photo.setSmallUrl(imageUrl);
            photo.setLargeUrl("https://devstudy-net.github.io/myphotos-com-test-images/large.jpg");
            photo.setOriginalUrl(imageUrl);
            photo.setViews(random.nextInt(100) * 10 + 1);
            photo.setDownloads(random.nextInt(20) * 10 + 1);
            photo.setCreated(new Date());

            profile.setPhotoCount(profile.getPhotoCount() + 1);
            photos.add(photo);
        }

        return Collections.unmodifiableList(photos);
    }

    private static Profile createProfile() {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setUid("richard-hendricks");
        profile.setCreated(new Date());
        profile.setFirstName("Richard");
        profile.setLastName("Hendricks");
        profile.setJobTitle("CEO of Pied Piper");
        profile.setLocation("Los Angeles, California");
        profile.setAvatarUrl("https://devstudy-net.github.io/myphotos-com-test-images/Richard-Hendricks.jpg");

        return profile;
    }
}
