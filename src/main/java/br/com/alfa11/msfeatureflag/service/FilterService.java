package br.com.alfa11.msfeatureflag.service;


import br.com.alfa11.msfeatureflag.model.Filter;
import br.com.alfa11.msfeatureflag.repository.FilterRepository;
import br.com.alfa11.msfeatureflag.util.DataUtil;
import br.com.alfa11.msfeatureflag.util.UlidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilterService {
    @Autowired
    FilterRepository filterRepository;

    public Iterable<Filter> getFilters() {
        return filterRepository.findAll();
    }
    public Optional<Filter> getFilterById(String id) {
        return filterRepository.findById(id);
    }
    public Filter save(Filter filter) {
        filter.setFilterId(UlidGenerator.generate());
        filter.setModifiedAt(DataUtil.getAgoraCompleta());
        return filterRepository.save(filter);
    }
    public Filter update(Filter filter) {
        filter.setModifiedAt(DataUtil.getAgoraCompleta());
        return filterRepository.save(filter);
    }
    public void deleteFilterById(String id) {
        filterRepository.deleteById(id);
    }

}
