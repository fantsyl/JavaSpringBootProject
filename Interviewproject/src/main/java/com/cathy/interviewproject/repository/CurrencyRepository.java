package com.cathy.interviewproject.repository;

import com.cathy.interviewproject.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {

}
