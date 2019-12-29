<%-- 
    Document   : admin
    Created on : Dec 3, 2019, 3:31:37 PM
    Author     : Shang
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <s:if test="%{#session.ACCOUNT == null || #session.ACCOUNT.role != 0}">
            <a href="Load" id="checkRole"></a>
            <script>
                document.getElementById("checkRole").click();
            </script>
        </s:if>

        <h1>Admin Page</h1>

        <s:if test="%{listHotel != null}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>HotelID</th>
                        <th>HotelName</th>
                        <th>Address</th>
                        <th>Phone</th>

                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="%{listHotel}" status="counter">
                        <tr>
                            <td><s:property value="%{#counter.count}"/></td>
                            <td><s:property value="%{id}"/></td>
                            <td><s:property value="%{name}"/></td>
                            <td><s:property value="%{address}"/></td>
                            <td><s:property value="%{phone}"/></td>
                        </tr>
                    </s:iterator>   
                </tbody>
            </table>





        </s:if>
    </body>
</html>
