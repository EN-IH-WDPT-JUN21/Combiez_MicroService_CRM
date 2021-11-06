package com.ironhack.microservice_homework_account;

import com.ironhack.microservice_homework_account.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //Report Mean number of employeeCount for all Accounts
    @Query("SELECT AVG(employeeCount) FROM Account")
    Optional<Double> findMeanEmployeeCount();

    // *** Median Report _ h2 can give list of all employeecounts in an ordered int array,
    // it needs a second step to find the median from this ***
    @Query("SELECT employeeCount FROM Account order by employeeCount")
    int[] findMedianEmployeeCount();


    //Report Maximum number of employeeCount for all Accounts
    @Query("SELECT MAX(employeeCount) FROM Account")
    Optional<Integer> findMaxEmployeeCount();

    //Report Minimum number of employeeCount for all Accounts
    @Query("SELECT MIN(employeeCount) FROM Account")
    Optional<Integer> findMinEmployeeCount();

}
