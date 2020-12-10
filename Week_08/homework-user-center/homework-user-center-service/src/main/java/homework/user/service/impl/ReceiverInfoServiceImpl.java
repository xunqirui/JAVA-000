package homework.user.service.impl;

import homework.user.entity.ReceiverInfo;
import homework.user.repository.ReceiverInfoRepository;
import homework.user.service.ReceiverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ReceiverInfoServiceImpl
 *
 * @author qrXun on 2020/12/9
 */
@Service
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    @Autowired
    private ReceiverInfoRepository receiverInfoRepository;

    @Override
    public Iterable<ReceiverInfo> findAll() {
        return receiverInfoRepository.findAll();
    }
}
