package cat.gencat.agaur.hexastock.adapter;

import cat.gencat.agaur.hexastock.application.port.in.*;
import cat.gencat.agaur.hexastock.application.port.out.PortfolioPort;
import cat.gencat.agaur.hexastock.application.port.out.StockPriceProviderPort;
import cat.gencat.agaur.hexastock.application.port.out.TransactionPort;
import cat.gencat.agaur.hexastock.application.service.*;
import cat.gencat.agaur.hexastock.model.service.HoldingPerformanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring application configuration, making Spring beans from services defined in application
 * module.
 *
 * @author Sven Woltmann
 */
@SpringBootApplication
public class SpringAppConfig {

  @Autowired
  TransactionPort transactionPort;

  @Autowired
  StockPriceProviderPort stockPriceProviderPort;

  @Autowired
  PortfolioPort portfolioPort;

  @Bean
  ReportingUseCase getReportingUseCase() {
    return new ReportingService(transactionPort, stockPriceProviderPort, portfolioPort, holdingPerformanceCalculator() );
  }

  @Bean
  HoldingPerformanceCalculator holdingPerformanceCalculator() {
    return new HoldingPerformanceCalculator();
  }

  @Bean
  GetStockPriceUseCase getStockPriceUseCase() {
    return new GetStockPriceService(stockPriceProviderPort);
  }

  @Bean
  PortfolioManagementUseCase getPortfolioManagementUseCase() {
    return new PortfolioManagementService(portfolioPort, transactionPort);
  }

  @Bean
  PortfolioStockOperationsUseCase getPortfolioStockOperationsUseCase() {
    return new PortfolioStockOperationsService(portfolioPort, stockPriceProviderPort, transactionPort);
  }

  @Bean
  TransactionUseCase getTransactionUseCase() {
    return new TransactionService(transactionPort);
  }

  /**
   * Main Spring Boot application class for the HexaStock financial portfolio management system.
   *
   * <p>This application implements a stock portfolio management system using hexagonal architecture
   * and Domain-Driven Design principles. It serves as the entry point for the application
   * and configures Spring Boot components.</p>
   *
   * <p>The application is structured according to hexagonal architecture:</p>
   * <ul>
   *   <li>Domain Model - Core business entities and logic</li>
   *   <li>Application Layer - Use cases and ports</li>
   *   <li>Infrastructure Layer - Adapters for external systems and frameworks</li>
   * </ul>
   *
   * @see org.springframework.boot.SpringApplication
   * @see SpringBootApplication
   */
  public static class HexaStockApplication {
      /**
       * The main method that serves as the entry point for the HexaStock application.
       *
       * @param args Command line arguments passed to the application
       */
      public static void main(String[] args) {
          SpringApplication.run(SpringAppConfig.class, args);
      }
  }
}