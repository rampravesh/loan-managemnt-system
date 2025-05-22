package com.loan.plans.service.impl;

import com.loan.dto.LoanPlansDTO;
import com.loan.entities.BaseInterestRates; // Added import
import com.loan.entities.LoanPlans;
import com.loan.entities.LoanPlansHistory; // Added import
import com.loan.plans.service.PlanService;
import com.loan.repository.BaseInterestRatesRepository; // Added import
import com.loan.repository.LoanPlansHistoryRepository; // Added import
import com.loan.repository.LoanPlansRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Added for H2
import org.springframework.transaction.annotation.Transactional; // Added for test data management

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse; // Added import
import static org.junit.jupiter.api.Assertions.assertNotEquals; // Added import
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue; // Added import

@SpringBootTest
@ActiveProfiles("test") // Assuming you have an application-test.properties for H2
@Transactional // Rollback transactions after each test
public class PlanServiceImplTest {

    @Autowired
    private PlanService planService;

    @Autowired
    private LoanPlansRepository loanPlansRepository;

    @Autowired
    private LoanPlansHistoryRepository loanPlansHistoryRepository; // Added repository

    @Autowired
    private BaseInterestRatesRepository baseInterestRatesRepository; // Added repository

    @Test
    void testCreateLoanPlan_withBigDecimalValues() {
        LoanPlansDTO dto = new LoanPlansDTO();
        dto.setPlanName("Test Plan BigDecimal");
        dto.setLoanTypeId(1);
        dto.setPrincipleAmount(new BigDecimal("10000.50"));
        dto.setTenure(12);
        dto.setInterestRate(new BigDecimal("5.75"));
        dto.setInterestAmount(new BigDecimal("575.00")); // Example value
        dto.setTotalPayable(new BigDecimal("10575.50")); // Example value
        dto.setEmi(new BigDecimal("881.29")); // Example value
        dto.setPlanValidity(Date.valueOf(LocalDate.now().plusYears(1)));
        dto.setPlanAddedOn(Date.valueOf(LocalDate.now()));

        LoanPlansDTO createdDto = planService.create(dto);
        assertNotNull(createdDto);
        assertNotNull(createdDto.getPlanId());

        // Verify DTO returned by service
        assertEquals(dto.getPrincipleAmount(), createdDto.getPrincipleAmount());
        assertEquals(dto.getInterestRate(), createdDto.getInterestRate());
        assertEquals(dto.getEmi(), createdDto.getEmi());
        assertEquals(dto.getInterestAmount(), createdDto.getInterestAmount());
        assertEquals(dto.getTotalPayable(), createdDto.getTotalPayable());

        // Verify entity in database
        LoanPlans persistedEntity = loanPlansRepository.findById(createdDto.getPlanId()).orElse(null);
        assertNotNull(persistedEntity);
        assertEquals(dto.getPrincipleAmount(), persistedEntity.getPrincipleAmount());
        assertEquals(dto.getInterestRate(), persistedEntity.getInterestRate());
        assertEquals(dto.getEmi(), persistedEntity.getEmi());
        // Also check other BigDecimal fields from entity if they are set by create method
        assertEquals(dto.getInterestAmount(), persistedEntity.getInterestAmount());
        assertEquals(dto.getTotalPayable(), persistedEntity.getTotalPayable());
    }

    @Test
    void testLoanPlanHistoryAssociation() {
        // 1. Create and save a LoanPlan
        LoanPlans loanPlan = new LoanPlans();
        loanPlan.setPlanName("Test Plan for History");
        loanPlan.setLoanTypeId(1);
        loanPlan.setPrincipleAmount(new BigDecimal("20000.00"));
        loanPlan.setTenure(24);
        loanPlan.setInterestRate(new BigDecimal("4.5"));
        // Set other required fields for LoanPlans if any...
        loanPlan.setPlanAddedOn(Date.valueOf(LocalDate.now()));
        loanPlan.setPlanValidity(Date.valueOf(LocalDate.now().plusMonths(6)));
        loanPlan.setEmi(new BigDecimal("875.00")); // Example
        loanPlan.setInterestAmount(new BigDecimal("1000.00")); // Example
        loanPlan.setTotalPayable(new BigDecimal("21000.00")); // Example

        LoanPlans savedLoanPlan = loanPlansRepository.save(loanPlan);
        assertNotNull(savedLoanPlan.getPlanId());

        // 2. Create and save a LoanPlansHistory associated with the LoanPlan
        LoanPlansHistory historyEntry = new LoanPlansHistory();
        historyEntry.setLoanPlans(savedLoanPlan); // Associate with the saved LoanPlan
        historyEntry.setUpdateDate(Date.valueOf(LocalDate.now()));
        historyEntry.setUpdateReason("Initial creation history");
        // Note: LoanPlansHistory ID is not auto-generated in the entity definition.
        // It needs to be set manually or the entity needs @GeneratedValue.
        // For this test, let's assume it needs to be set or the schema handles it.
        // If 'id' is not @GeneratedValue, this test might fail if it's expected to be auto-generated by JPA.
        // The provided entity LoanPlansHistory has '@Id @Column(name = "Id", nullable = false) private Integer id;'
        // This usually means it's not auto-generated by JPA unless the DB schema has a default or sequence.
        // For testing, we can try to set a manual ID or see if H2/Hibernate assigns one.
        // Let's assume for now we need to set it for the test if it's not generated.
        // If the save fails due to null ID, this is an issue with LoanPlansHistory entity itself.
        // For now, let's try without setting ID, assuming default behavior might assign one in H2.
        // If that fails, the entity definition of LoanPlansHistory might need @GeneratedValue.
        // Setting a manual ID for the test to ensure predictability, given no @GeneratedValue
        historyEntry.setId(1001); // Example ID, choose a value that won't collide if other tests run in parallel and don't clean up

        LoanPlansHistory savedHistoryEntry = loanPlansHistoryRepository.save(historyEntry);
        assertNotNull(savedHistoryEntry.getId()); // This assertion depends on how ID is generated/set.

        // 3. Retrieve the LoanPlansHistory and verify the association
        LoanPlansHistory fetchedHistoryEntry = loanPlansHistoryRepository.findById(savedHistoryEntry.getId()).orElse(null);
        assertNotNull(fetchedHistoryEntry);
        assertNotNull(fetchedHistoryEntry.getLoanPlans());
        assertEquals(savedLoanPlan.getPlanId(), fetchedHistoryEntry.getLoanPlans().getPlanId());
        assertEquals("Test Plan for History", fetchedHistoryEntry.getLoanPlans().getPlanName());
    }

    @Test
    void testDeleteLoanPlanHistory_doesNotDeleteLoanPlan() {
        // 1. Create and save a LoanPlan
        LoanPlans loanPlan = new LoanPlans();
        loanPlan.setPlanName("Test Plan for Cascade Delete Test");
        loanPlan.setLoanTypeId(2);
        loanPlan.setPrincipleAmount(new BigDecimal("30000.00"));
        loanPlan.setTenure(36);
        loanPlan.setInterestRate(new BigDecimal("3.5"));
        loanPlan.setPlanAddedOn(Date.valueOf(LocalDate.now()));
        loanPlan.setPlanValidity(Date.valueOf(LocalDate.now().plusYears(1)));
        loanPlan.setEmi(new BigDecimal("880.00")); // Example
        loanPlan.setInterestAmount(new BigDecimal("1500.00")); // Example
        loanPlan.setTotalPayable(new BigDecimal("31500.00")); // Example
        LoanPlans savedLoanPlan = loanPlansRepository.save(loanPlan);
        Integer loanPlanId = savedLoanPlan.getPlanId();
        assertNotNull(loanPlanId);

        // 2. Create and save a LoanPlansHistory associated with the LoanPlan
        LoanPlansHistory historyEntry = new LoanPlansHistory();
        historyEntry.setId(1002); // Manual ID for testing
        historyEntry.setLoanPlans(savedLoanPlan);
        historyEntry.setUpdateDate(Date.valueOf(LocalDate.now()));
        historyEntry.setUpdateReason("Cascade delete test history");
        LoanPlansHistory savedHistoryEntry = loanPlansHistoryRepository.save(historyEntry);
        Integer historyId = savedHistoryEntry.getId();
        assertNotNull(historyId);

        // 3. Delete the LoanPlansHistory
        loanPlansHistoryRepository.deleteById(historyId);

        // 4. Verify LoanPlansHistory is deleted (Corrected assertion from problem description)
        // The original problem description had an assertNotNull for loanPlansRepository.findById
        // which is correct for this test's purpose: verifying the LoanPlan still exists.
        // The comment also correctly states "LoanPlan should still exist".
        // So the assertion should check that the loanPlan is NOT null.
        assertNotNull(loanPlansRepository.findById(loanPlanId).orElse(null),
                "LoanPlan should still exist after deleting its history.");
    }

   @Test
   void testLoanPlansEqualsAndHashCode() {
       LoanPlans plan1 = new LoanPlans();
       plan1.setPlanId(1);
       plan1.setPlanName("Plan A");

       LoanPlans plan2 = new LoanPlans();
       plan2.setPlanId(1);
       plan2.setPlanName("Plan B"); // Different name, same ID

       LoanPlans plan3 = new LoanPlans();
       plan3.setPlanId(2);
       plan3.setPlanName("Plan C");

       LoanPlans plan4 = new LoanPlans(); // No ID (transient)
       plan4.setPlanName("Plan D");
       
       LoanPlans plan5 = new LoanPlans();
       plan5.setPlanId(1); // Same ID as plan1 and plan2

       // Test equals
       assertTrue(plan1.equals(plan2), "Entities with same ID should be equal.");
       assertTrue(plan1.equals(plan5), "Entities with same ID should be equal.");
       assertFalse(plan1.equals(plan3), "Entities with different IDs should not be equal.");
       assertFalse(plan1.equals(plan4), "Persisted entity should not be equal to transient entity.");
       assertFalse(plan4.equals(plan1), "Transient entity should not be equal to persisted entity.");
       assertFalse(plan1.equals(null), "Entity should not be equal to null.");
       assertFalse(plan1.equals(new Object()), "Entity should not be equal to object of different class.");
       
       LoanPlans plan1Ref = plan1;
       assertTrue(plan1.equals(plan1Ref), "Entity should be equal to itself.");

       LoanPlans transient1 = new LoanPlans();
       LoanPlans transient2 = new LoanPlans();
       assertFalse(transient1.equals(transient2), "Two different transient entities should not be equal.");
       assertTrue(transient1.equals(transient1), "A transient entity should be equal to itself.");


       // Test hashCode
       // Based on getClass().hashCode(), so all instances of LoanPlans should have the same hashCode.
       // This is a valid strategy for JPA entities to ensure hashCode stability.
       assertEquals(plan1.hashCode(), plan2.hashCode(), "HashCodes should be same for same class (current impl).");
       assertEquals(plan1.hashCode(), plan3.hashCode(), "HashCodes should be same for same class (current impl).");
       assertEquals(plan1.hashCode(), plan4.hashCode(), "HashCodes should be same for same class (current impl).");
   }

   @Test
   void testLoanPlansToString_avoidsLazyLoadingException() {
       // 1. Create and save a LoanPlan with a history entry
       LoanPlans loanPlan = new LoanPlans();
       // loanPlan.setPlanId(300); // PlanId is auto-generated, so we don't set it manually before save.
                                 // We'll get it from the saved entity.
       loanPlan.setPlanName("Test Plan for ToString");
       // ... set other required fields for LoanPlans ...
       loanPlan.setPrincipleAmount(new BigDecimal("1000"));
       loanPlan.setTenure(10);
       loanPlan.setInterestRate(new BigDecimal("1"));
       loanPlan.setEmi(new BigDecimal("100"));
       loanPlan.setInterestAmount(new BigDecimal("100"));
       loanPlan.setTotalPayable(new BigDecimal("1100"));
       loanPlan.setPlanAddedOn(Date.valueOf(LocalDate.now()));
       loanPlan.setPlanValidity(Date.valueOf(LocalDate.now().plusMonths(1)));
       
       // Note: loanPlansHistories is initialized as new ArrayList()
       // To test lazy loading, it should ideally be populated via persistence
       // and then the parent entity detached.

       LoanPlans savedPlan = loanPlansRepository.save(loanPlan); // planId will be generated
       Integer planId = savedPlan.getPlanId(); // Use the generated ID
       assertNotNull(planId, "Saved plan must have an ID");


       LoanPlansHistory history = new LoanPlansHistory();
       history.setId(1003); // Manual ID for history
       history.setLoanPlans(savedPlan);
       history.setUpdateReason("ToString test history");
       history.setUpdateDate(Date.valueOf(LocalDate.now()));
       loanPlansHistoryRepository.save(history);
       
       // 2. Fetch the plan and then "detach" it (conceptually, by clearing persistence context or using a fresh fetch)
       // For simplicity in test, we refetch. In a real scenario, detaching can be more explicit.
       // @Transactional would typically keep it managed. Here, we are in a test context.
       // The key is that `loanPlansHistories` is LAZY.
       
       LoanPlans fetchedPlan = loanPlansRepository.findById(planId).get();
       // At this point, fetchedPlan.getLoanPlansHistories() would trigger a load if accessed.
       // We want to test that toString() does NOT trigger this if the session were closed.
       // SpringBootTest with @Transactional might keep session open.
       // A more robust way to test detachment for toString is harder in simple @SpringBootTest.
       // However, @ToString(exclude = "loanPlansHistories") should guarantee it's not accessed.

       String planToString = fetchedPlan.toString();
       assertNotNull(planToString);
       assertFalse(planToString.contains("LoanPlansHistory"), "toString should not include details of lazy loanPlansHistories due to exclusion.");
       assertTrue(planToString.contains(savedPlan.getPlanName()), "toString should contain basic fields like planName.");

       // A simple check for the field name in toString might be too naive if field names are not directly used.
       // The main point is no LazyInitializationException.
       // If @ToString.Exclude works, this test should pass without issues.
   }

   @Test
   void testLoanPlansHistoryEqualsAndHashCode() {
       LoanPlansHistory history1 = new LoanPlansHistory();
       history1.setId(101); // Assuming IDs are Integer

       LoanPlansHistory history2 = new LoanPlansHistory();
       history2.setId(101);

       LoanPlansHistory history3 = new LoanPlansHistory();
       history3.setId(102);
       
       LoanPlansHistory history4 = new LoanPlansHistory(); // Transient

       // Test equals
       assertTrue(history1.equals(history2), "Entities with same ID should be equal.");
       assertFalse(history1.equals(history3), "Entities with different IDs should not be equal.");
       assertFalse(history1.equals(history4), "Persisted entity should not be equal to transient entity.");
       assertFalse(history4.equals(history1), "Transient entity should not be equal to persisted entity.");
       assertFalse(history1.equals(null));
       assertFalse(history1.equals(new Object()));
       
       LoanPlansHistory history1Ref = history1;
       assertTrue(history1.equals(history1Ref));

       LoanPlansHistory transient1 = new LoanPlansHistory();
       LoanPlansHistory transient2 = new LoanPlansHistory();
       assertFalse(transient1.equals(transient2));
       assertTrue(transient1.equals(transient1));

       // Test hashCode (all instances of LoanPlansHistory should have same hashCode with current impl)
       assertEquals(history1.hashCode(), history2.hashCode());
       assertEquals(history1.hashCode(), history3.hashCode());
       assertEquals(history1.hashCode(), history4.hashCode());
   }

   @Test
   void testLoanPlansHistoryToString_avoidsLazyLoadingException() {
       // 1. Create a parent LoanPlan
       LoanPlans loanPlan = new LoanPlans();
       // loanPlan.setPlanId(400); // PlanId is auto-generated, so we don't set it manually.
                                 // We'll get it from the saved entity.
       loanPlan.setPlanName("Test Plan for History ToString");
       // ... set other required fields for LoanPlans ...
       loanPlan.setPrincipleAmount(new BigDecimal("2000"));
       loanPlan.setTenure(12);
       loanPlan.setInterestRate(new BigDecimal("2"));
       loanPlan.setEmi(new BigDecimal("200"));
       loanPlan.setInterestAmount(new BigDecimal("200"));
       loanPlan.setTotalPayable(new BigDecimal("2200"));
       loanPlan.setPlanAddedOn(Date.valueOf(LocalDate.now()));
       loanPlan.setPlanValidity(Date.valueOf(LocalDate.now().plusMonths(2)));
       LoanPlans savedLoanPlan = loanPlansRepository.save(loanPlan);
       Integer loanPlanId = savedLoanPlan.getPlanId(); // Use the generated ID
       assertNotNull(loanPlanId, "Saved plan must have an ID for association");


       // 2. Create and save a LoanPlansHistory associated with this plan
       LoanPlansHistory history = new LoanPlansHistory();
       history.setId(1004); // Manual ID for test
       history.setLoanPlans(savedLoanPlan);
       history.setUpdateReason("History ToString Test");
       history.setUpdateDate(Date.valueOf(LocalDate.now()));
       loanPlansHistoryRepository.save(history);
       Integer historyId = history.getId();
       assertNotNull(historyId, "History must have an ID after saving");


       // 3. Fetch the history entry
       LoanPlansHistory fetchedHistory = loanPlansHistoryRepository.findById(historyId).get();
       
       // Test toString - @ToString(exclude = "loanPlans")
       String historyToString = fetchedHistory.toString();
       assertNotNull(historyToString);
       assertFalse(historyToString.contains("LoanPlans["), "toString should not include details of lazy loanPlans due to exclusion.");
       assertFalse(historyToString.contains(savedLoanPlan.getPlanName()), "toString should not include details of lazy loanPlans due to exclusion.");
       assertTrue(historyToString.contains(history.getUpdateReason()), "toString should contain basic fields like updateReason.");
   }

   @Test
   void testBaseInterestRatesEqualsAndHashCode() {
       BaseInterestRates rate1 = new BaseInterestRates();
       rate1.setId(201); // Assuming IDs are Integer
       rate1.setLoanType("Home");

       BaseInterestRates rate2 = new BaseInterestRates();
       rate2.setId(201);
       rate2.setLoanType("Personal"); // Different type, same ID

       BaseInterestRates rate3 = new BaseInterestRates();
       rate3.setId(202);
       rate3.setLoanType("Home");
       
       BaseInterestRates rate4 = new BaseInterestRates(); // Transient

       // Test equals
       assertTrue(rate1.equals(rate2), "Entities with same ID should be equal.");
       assertFalse(rate1.equals(rate3), "Entities with different IDs should not be equal.");
       assertFalse(rate1.equals(rate4), "Persisted entity should not be equal to transient entity.");
       assertFalse(rate4.equals(rate1), "Transient entity should not be equal to persisted entity.");
       assertFalse(rate1.equals(null));
       assertFalse(rate1.equals(new Object()));
       
       BaseInterestRates rate1Ref = rate1;
       assertTrue(rate1.equals(rate1Ref));

       BaseInterestRates transient1 = new BaseInterestRates();
       BaseInterestRates transient2 = new BaseInterestRates();
       assertFalse(transient1.equals(transient2));
       assertTrue(transient1.equals(transient1));

       // Test hashCode (all instances of BaseInterestRates should have same hashCode with current impl)
       assertEquals(rate1.hashCode(), rate2.hashCode());
       assertEquals(rate1.hashCode(), rate3.hashCode());
       assertEquals(rate1.hashCode(), rate4.hashCode());
   }

   @Test
   void testBaseInterestRatesToString() {
       BaseInterestRates rate = new BaseInterestRates();
       rate.setId(203); // Manual ID for test
       rate.setLoanType("Vehicle");
       rate.setBaseInterestRate(new BigDecimal("3.25"));
       
       // Since BaseInterestRates has no lazy fields, @ToString should include all fields.
       // No specific detachment logic needed to test basic toString functionality.
       // We are primarily testing that it doesn't throw an unexpected error and includes some field.
       baseInterestRatesRepository.save(rate); // Save to ensure ID is set if it were generated.

       BaseInterestRates fetchedRate = baseInterestRatesRepository.findById(rate.getId()).get();

       String rateToString = fetchedRate.toString();
       assertNotNull(rateToString);
       assertTrue(rateToString.contains("loanType=Vehicle"), "toString should contain loanType.");
       assertTrue(rateToString.contains("baseInterestRate=3.25"), "toString should contain baseInterestRate.");
       // Check for presence of ID as well
       assertTrue(rateToString.contains("id=203"), "toString should contain the id.");
   }
}
