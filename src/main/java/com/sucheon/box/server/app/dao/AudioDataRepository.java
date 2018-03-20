package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.device.AudioData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AudioDataRepository extends JpaRepository<AudioData, Long> {
    AudioData findTopById(Long id);
}
