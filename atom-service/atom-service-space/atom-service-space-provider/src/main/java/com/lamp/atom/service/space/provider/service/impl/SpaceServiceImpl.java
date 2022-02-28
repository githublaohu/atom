package com.lamp.atom.service.space.provider.service.impl;

import com.lamp.atom.space.operator.service.SpaceService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("spaceService")
@Component
public class SpaceServiceImpl implements SpaceService {
}
