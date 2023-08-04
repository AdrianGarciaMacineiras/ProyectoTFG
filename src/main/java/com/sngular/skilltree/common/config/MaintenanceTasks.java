package com.sngular.skilltree.common.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class MaintenanceTasks {

  private Neo4jClient neo4jClient;

  private AssignableConfiguration assignableConfiguration;

  @Scheduled(cron = "0 0 * * * *")
  public void fixMissingDeleted() {

    neo4jClient.query("MATCH (n) WHERE n.deleted IS NULL SET n.deleted = false return COUNT(n)").fetch().first()
               .ifPresent(result -> log.debug("Job Set Deleted result :" + result));
  }

  @Scheduled(cron = "0 0 * * * *")
  public void fixAssignable() {
    final var queryStr = new StringBuilder();
    queryStr.append("""
                      MATCH (c:Client)--(pr:Project)--(po:Position)-[k:COVER]-(m:People) -- (t:Team)
                      WHERE c.name =~ "Singular.*"
                      and k.currentMonthActivity > 0.7
                      """);
    assignableConfiguration
      .getTitles()
      .forEach(title -> queryStr.append(" and not m.title =~\"").append(title).append(".*\" "));
    queryStr.append("and not pr.name in [").append(String.join(",", assignableConfiguration.getJobCodeList())).append("] ");
    queryStr.append("and not t.shortName in [").append(String.join(",", assignableConfiguration.getShortTeamNames())).append("] ");
    queryStr.append("""
                      SET m.assignable = true
                      RETURN true""");

    neo4jClient.query(queryStr.toString()).fetch().first().ifPresent(result -> log.debug("Job Set Assignable result :" + result));
  }
}
