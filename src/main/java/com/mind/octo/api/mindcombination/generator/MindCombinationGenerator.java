package com.mind.octo.api.mindcombination.generator;

import com.mind.octo.api.mind.entity.MindEntity;

import java.util.List;

public interface MindCombinationGenerator {

    List<String> generate(List<MindEntity> minds);
}