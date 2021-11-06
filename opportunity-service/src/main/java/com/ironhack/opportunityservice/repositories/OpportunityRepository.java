package com.ironhack.opportunityservice.repositories;

import com.ironhack.opportunityservice.dao.Opportunity;
import com.ironhack.opportunityservice.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

  @Query("SELECT o.salesRep, COUNT(o) FROM Opportunity o GROUP BY o.salesRep ORDER BY o.salesRep")
  List<Long[]> getCountOpportunityBySalesRep();

  @Query("SELECT o.salesRep, COUNT(o) FROM Opportunity o WHERE status = :status GROUP BY o.salesRep ORDER BY o.salesRep")
  List<Long[]> getCountOpportunityBySalesRepWithStatus(@Param("status") Status status);

  //Report Opportunities by Product
  @Query("SELECT o.product, count(o) FROM Opportunity o GROUP BY o.product ORDER BY o.product")
  List<Object[]> getCountOpportunityByProduct();

  //Report CLOSED-WON, CLOSED-LOST, and OPEN opportunities by Product (takes a parameter argument)
  @Query("SELECT o.product, COUNT(o) FROM Opportunity o WHERE status = :status GROUP BY o.product ORDER BY o.product")
  List<Object[]> getCountOpportunityByProductWithStatus(@Param("status") Status status);

  //Report Opportunities by Account
  @Query("SELECT o.account, COUNT(o) FROM Opportunity o GROUP BY o.account")
  List<Long[]> getCountOpportunityByAccount();

  //Report CLOSED-WON, CLOSED-LOST, and OPEN opportunities by Account (takes a parameter argument)
  @Query("SELECT o.account, COUNT(o) FROM Opportunity o WHERE status = :status GROUP BY o.account")
  List<Long[]> getCountOpportunityByAccountWithStatus(@Param("status") Status status);

  //Report mean number of products quantity for all Opportunities
  @Query("SELECT AVG(quantity) FROM Opportunity")
  Optional<Double> getMeanProductQuantity();

  // *** Median Report is needed JPQL can give list of all quantities in an ordered int array, needs a second step to find the median from this ***
  @Query("SELECT quantity FROM Opportunity order by quantity")
  List<Long> getMedianQuantityStep1();

  //Report Maximum  products quantity for all Opportunities
  @Query("SELECT MAX(quantity) FROM Opportunity")
  Optional<Long> getMaxProductQuantity();

  //Report Minimum  products quantity for all Opportunities
  @Query("SELECT MIN(quantity) FROM Opportunity")
  Optional<Long> getMinProductQuantity();

  //Report Mean number of Opportunities associated with an account
  @Query(value = "select avg(a.count_opportunity) from (select count(distinct id) as count_opportunity from opportunity group by account order by count_opportunity) a", nativeQuery = true)
  Optional<Double> getMeanOpportunitiesPerAccount();

  //Report Max number of Opportunities associated with an account
  @Query(value = "select max(a.count_opportunity) from (select count(distinct id) as count_opportunity from opportunity group by account order by count_opportunity) a", nativeQuery = true)
  Optional<Double> getMaxOpportunitiesPerAccount();

  //Report Min number of Opportunities associated with an account
  @Query(value = "select min(a.count_opportunity) from (select count(distinct id) as count_opportunity from opportunity group by account order by count_opportunity) a", nativeQuery = true)
  Optional<Double> getMinOpportunitiesPerAccount();
}
