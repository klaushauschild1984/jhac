package com.sap.hybris.hac.flexiblesearch;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.util.Locale;

import static com.sap.hybris.hac.util.Utils.readLines;

/**
 * Flexible search / SQL query
 *
 * @author Klaus Hauschild
 */
@Builder(builderClassName = "FlexibleSearchQueryBuilder")
@Getter
@ToString
public class FlexibleSearchQuery {

  private String flexibleSearchQuery;
  private String sqlQuery;
  private int maxCount;
  private String user;
  private Locale locale;
  private boolean commit;

  public static class FlexibleSearchQueryBuilder {

    public FlexibleSearchQueryBuilder flexibleSearchQuery(final String flexibleSearchQuery) {
      this.flexibleSearchQuery = flexibleSearchQuery;
      return this;
    }

    public FlexibleSearchQueryBuilder flexibleSearchQuery(final InputStream flexibleSearchQuery) {
      this.flexibleSearchQuery = readLines(flexibleSearchQuery, "flexible search query");
      return this;
    }

    public FlexibleSearchQueryBuilder sqlQuery(final String sqlQuery) {
      this.sqlQuery = sqlQuery;
      return this;
    }

    public FlexibleSearchQueryBuilder sqlQuery(final InputStream sqlQuery) {
      this.sqlQuery = readLines(sqlQuery, "sql query");
      return this;
    }

    public FlexibleSearchQuery build() {
      // validation
      if (flexibleSearchQuery == null && sqlQuery == null) {
        throw new IllegalArgumentException(
            "either flexibleSearchQuery or sqlQuery must not be null");
      }
      if (flexibleSearchQuery != null && sqlQuery != null) {
        throw new IllegalArgumentException("either flexibleSearchQuery or sqlQuery must be used");
      }

      // default values
      if (maxCount <= 0) {
        maxCount = 200;
      }
      if (user == null) {
        user = "admin";
      }
      if (locale == null) {
        locale = Locale.ENGLISH;
      }

      // build
      return new FlexibleSearchQuery(flexibleSearchQuery, sqlQuery, maxCount, user, locale, commit);
    }
  }
}
