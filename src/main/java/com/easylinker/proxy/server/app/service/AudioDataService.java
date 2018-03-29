package com.easylinker.proxy.server.app.service;

import com.easylinker.proxy.server.app.dao.AudioDataRepository;
import com.easylinker.proxy.server.app.model.device.AudioData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 声音数据Service
 */
@Service("AudioDataService")
public class AudioDataService {
    @Autowired
    AudioDataRepository audioDataRepository;

    public AudioData findAAudioData(Long id) {
        return audioDataRepository.findTopById(id);

    }

    public void save(AudioData audioData) {
        audioDataRepository.save(audioData);
    }
}
