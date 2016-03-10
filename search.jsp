<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <link rel="stylesheet" type="text/css" href="resources/css/bulma.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/search.css"> 

    <title>Search</title>
  </head>

  <body>
    <!-- Hero -->
    <section class="hero is-info is-left is-bold">
      <div class="hero-content">
        <div class="container is-fluid">
          <div class="columns is-mobile">
            <div class="column is-3-mobile is-3-tablet is-2-desktop">
              <p class="title is-3 is-text-centered"><a href="index.html">Search ICS</a></p>
            </div>
            <div class="column is-8-mobile is-6-tablet is-8-desktop">
              <form name="search" action="searchQuery" method="GET" id="search_form">
                <p class="control has-icon">
                  <input class="input" id="search_bar" name="param" type="text" placeholder="Search here!">
                  <input class="input" name="page" type="hidden" value=1>
                  <i class="fa fa-search"></i>
                </p>
              </form>
            </div>
            <div class="column is-1-mobile">
              
            </div>
          </div>
        </div>
      </div>
    </section>
    <br>

    <!-- Body -->
    <div class="container">
      <div class="box">
        <ul id="search_results">
          <c:set var="sep" scope="session" value=""/>
          <c:forEach items="${pages}" var="page">
            <li>
              <div class="content">
                <c:out value="${sep}" escapeXml="false"/>
                <p class="subtitle is-6" id="search_title"><a href="${page.url}">${page.url}</a><span class="tag is-pulled-right">${page.tfIdf}</span></p>
                <blockquote><c:out value="${page.context}" escapeXml="false"/></blockquote>
              </div>
            </li>
            <c:set var="sep" scope="session" value="<hr>"/>
          </c:forEach>
        </ul>
      </div>
    </div>
    <br>

    <!-- Pagination buttons -->

  </body>

  <script src="resources/scripts/jquery-2.2.1.min.js"></script>
  <script src="resources/scripts/search.js"></script>
</html>
