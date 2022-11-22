package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.Tag;
import com.bach.Commerce.model.dto.TagDTO;
import com.bach.Commerce.repo.dao.TagDAO;
import com.bach.Commerce.service.base.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {


    final TagDAO tagDao;

    @Override
    public List<TagDTO> getAllTag() {
        List<Tag> listTag = tagDao.getAllTag();

        List<TagDTO> listTagDTO = new ArrayList<>();

        for (Tag t : listTag) {

            TagDTO tagDTO = new TagDTO();

            tagDTO.setId(t.getId());
            tagDTO.setType(t.getType());

            listTagDTO.add(tagDTO);
        }

        return listTagDTO;
    }

}
