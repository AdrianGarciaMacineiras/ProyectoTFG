package com.tfg.skilltree.common.config;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class MaintenanceTasks {

  private final Neo4jClient neo4jClient;

  private final AssignableConfiguration assignableConfiguration;

  private String jobCodeStr;

  private String shortTeamName;

  @Scheduled(cron = "0 0 * * * *")
  public void fixMissingDeleted() {

    neo4jClient.query("MATCH (n) WHERE n.deleted IS NULL SET n.deleted = false return COUNT(n)").fetch().first()
               .ifPresent(result -> log.info("Job Set Deleted result :" + result));
  }

  @Scheduled(cron = "0 0 * * * *")
  public void fixMissingDedication() {

    neo4jClient.query("MATCH p=()-[r:COVER]->() SET r.dedication=100").fetch().first()
            .ifPresent(result -> log.info("Job Set Dedication result :" + result));
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
    queryStr.append("and not pr.name in [").append(strJoin(jobCodeStr, assignableConfiguration.getJobCodeList(), ',')).append("] ");
    queryStr.append("and not t.shortName in [").append(strJoin(shortTeamName, assignableConfiguration.getShortTeamNames(), ',')).append("] ");
    queryStr.append("""
                      SET m.assignable = true
                      RETURN true""");

    neo4jClient.query(queryStr.toString()).fetch().first().ifPresent(result -> log.info("Job Set Assignable result :" + result));
  }

  private String strJoin(String storeStr, final List<String> strList, final char delimiter) {
    if (StringUtils.isEmpty(storeStr)) {
      final var srtBuilder = new StringBuilder();
      final var strListIt = strList.iterator();
      while (strListIt.hasNext()) {
        srtBuilder.append('"').append(strListIt.next()).append('"');
        if (strListIt.hasNext()) {
          srtBuilder.append(delimiter);
        }
      }
      storeStr = srtBuilder.toString();
    }
    return storeStr;
  }
}
