<%-- 
    Document   : index
    Created on : Nov 24, 2019, 5:18:47 PM
    Author     : Shang
--%>

<%@taglib uri="/struts-tags" prefix="s" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking History</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/insertform.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginpage.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tableview.css"/>
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css"/>-->
        <style>
            .navbar {
                overflow: hidden;
                background-color: #333;
                font-family: Arial, Helvetica, sans-serif;
            }

            .navbar a {
                float: left;
                font-size: 16px;
                color: white;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
            }
            .navbar .button{
                float: right;
            }

            .dropdown {
                float: left;
                overflow: hidden;
            }

            .dropdown .dropbtn {
                cursor: pointer;
                font-size: 16px;  
                border: none;
                outline: none;
                color: white;
                padding: 14px 16px;
                background-color: inherit;
                font-family: inherit;
                margin: 0;
            }

            .navbar a:hover, .dropdown:hover .dropbtn, .dropbtn:focus {
                background-color: darkgray;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown-content a {
                float: none;
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
                text-align: left;
            }

            .dropdown-content a:hover {
                background-color: #ddd;
            }

            .show {
                display: block;
            }

            .sticky {
                position: fixed;
                top: 0;
                width: 100%;

            }

            input[type=number] {
                -moz-appearance:textfield;
                width: 25%;
            }
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
            }


            .form input[type=email],[type=tel] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }


        </style>

    </head>
    <body>
         <s:if test="%{#session.ACCOUNT == null || #session.ACCOUNT.role != 1}">
            <a href="Load" id="checkRole">aaaa</a>
            <script>
                document.getElementById("checkRole").click();
            </script>
        </s:if>
        <h3 style="background-color: black; color: white; text-align: center">Dù ai đi ngược về xuôi, muốn đi khách sạn phải tìm web tui !</h3>
        <div class="navbar" id="navbar">
            <a href="'">Home</a>

            <div class="dropdown">
                <button class="dropbtn" onclick="myFunction1()">Search by Date
                    <i class="fa fa-caret-down"></i>
                </button>
                <div class="dropdown-content " id="myDropdown1">
                    <s:form action="SearchHistory" theme="simple" method="POST">
                        <a>
                            <s:textfield title="Hotel name" cssClass="dropbtn" style="background-color: white; color: black" id="dateSearch" type="date" name="bookingDate"/>
                        </a> 
                        <a><button class="dropbtn" style="background-color: green">Search</button></a>
                    </s:form>
                </div>
            </div> 
            <!--script for date-->
            <script>
                document.getElementById("dateSearch").value = new Date().toISOString().slice(0, 10);

            </script>

            <a class="button" <s:if test="%{#session.ACCOUNT != null && #session.ACCOUNT.role != 0}">href="viewcart.jsp"</s:if><s:else>onclick="showForm()"</s:else>>View your Cart</a>
            <s:if test="%{#session.ACCOUNT != null}">
                <a class="button" href="Logout"><s:property value="%{#session.ACCOUNT.email}" /> ,Logout</a>
            </s:if>
            <s:else>
                <a class="button" href="#" onclick="showForm()" >Login</a>
            </s:else>
            <s:if test="%{#session.ACCOUNT != null}">
                <s:a href="ViewHistory" class="button">View History</s:a>
            </s:if>
        </div>

        <br/>
        <br/>



        <s:if test="%{listHistory != null}">
            <s:if test="%{listHistory.size() > 0}">
                <div style="overflow-x: auto">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>NO.</th>
                                <th>BookingID</th>
                                <th>Newest Date</th>
                                <th>TotalPrice</th>
                                <th>Discount(%)</th>
                                <th>ViewDetail</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        <tbody>

                            <s:iterator value="%{listHistory}" status="counter" var="dto">
                                <tr <s:if test="%{status == true}">style="background-color: aquamarine"</s:if><s:else>style="background-color: graytext"</s:else>>
                                    <td><s:property value="%{#counter.count}"/></td>
                                    <th>#<s:property value="%{bookingID}"/></th>
                                    <td><s:property value="%{bookingDate}" /></td>
                                    <td><s:property value="%{total}"/></td>
                                    <td><s:property value="%{discount * 100}"/>%</td>
                                    <td>
                                        <s:url id="LinkView" value="ViewDetailBooking">
                                            <s:param name="bookingID" value="%{bookingID}"/>
                                        </s:url>
                                        <a href="<s:property value="%{LinkView}"/>"><strong><span>View</span></strong></a>
                                    </td>
                                    <td>

                                        <s:if test="%{status == true}">
                                            <s:url id="LinkCancel" value="CancelBooking">
                                                <s:param name="bookingID" value="%{bookingID}"/>
                                            </s:url>
                                            <a href="#" onclick="confirmCancel(this, '<s:property value="%{LinkCancel}"/>');">
                                                <strong><span>Cancel</span></strong>
                                            </a>
                                        </s:if>

                                    </td>
                                </tr>

                            </s:iterator>
                        <script>
                            function confirmCancel(link, address) {
                                var isDelete = confirm("Do you want to cancel booking order ?");
                                if (isDelete === true) {
                                    link.href = address;
                                    link.onclick = "";
                                    link.click();
                                }
                            }
                        </script>
                        </tbody>
                    </table>
                </div>


            </s:if>
            <s:else>
                Empty History
            </s:else>
        </s:if>

        <s:if test="%{listDetails != null}">
            <s:if test="%{listDetails.size() > 0}">
                <div style="overflow-x: auto">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>TypeID</th>
                                <th>Hotel</th>
                                <th>Amount</th>
                                <th>Price</th>
                                <th>Checkin</th>
                                <th>Checkout</th>
                                <th>Feedback</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="%{listDetails}" status="counter" var="dto">
                                <tr>
                                    <td><s:property value="%{#counter.count}"/></td>
                                    <td>
                                        <s:iterator value="%{#session.HOTELS}" var="hotelDTO">
                                            <s:if test="%{#hotelDTO.id == #dto.getHotelID()}">
                                                <s:property value="%{#hotelDTO.name}"/>
                                            </s:if>
                                        </s:iterator>
                                    </td>
                                    <td>

                                        <s:iterator value="%{listRoomType}" var="typeDTO">
                                            <s:if test="%{#typeDTO.id == #dto.typeID}">
                                                <s:property value="%{#typeDTO.name}"/>
                                            </s:if>
                                        </s:iterator>
                                    </td>
                                    <td><s:property value="%{amount}"/></td>
                                    <td><s:property value="%{price}"/></td>
                                    <td><s:property value="%{checkin}"/></td>
                                    <td><s:property value="%{checkout}"/></td>
                                    <td><a href="#" onclick="showFeedback('<s:property value="%{#dto.getHotelID()}"/>')">Feedback</a></td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>

                </div>
            </s:if>
        </s:if>   




        <!--LoginModal-->
        <div id="myModal" class="modal">
            <div class="modal-content">

                <span class="close">&times;</span>
                <br/>   
                <h3>Login</h3>
                <h5 style="color: red"><s:property value="%{invalid}"/></h5>
                <form class="form" action="Login" method="POST">
                    <input  type="email" placeholder="Email" name="email"/><br/>
                    <input  type="password" placeholder="Password" name="password"/><br/>
                    <button type="submit" value="Login">Login</button>
                </form>
                <button onclick="showRegister()">Register Account</button>
            </div>
        </div>

        <!--RegisterModal-->
        <div id="myRegisterModal" class="modal">
            <div class="modal-content">

                <span class="close">&times;</span>
                <br/>   
                <h3>Register Account</h3>
                <h5 style="color: red"><s:property value="%{invalid}"/></h5>
                <form class="form" action="Register" method="POST">
                    <input  type="email" required="" placeholder="Email" name="userID"/><br/>
                    <input id="password" type="password" required="" placeholder="Password" name="password"/><br/>
                    <input id="confirm-pass" type="password" required="" placeholder="Confirm" name="confirm"/><br/>
                    <input type="text" placeholder="Address" required="" name="address"/><br/>
                    <input type="tel" pattern="[0-9]{10}" required="" placeholder="Phone" name="phone"/>
                    <button type="submit" value="Login">Register</button>
                </form>
            </div>
        </div>
        <script>
            var password = document.getElementById("password"), confirm_pass = document.getElementById("confirm-pass");
            function validatePassword() {
                if (password.value !== confirm.value) {
                    confirm_pass.setCustomValidity("Confirm must be match with Password!");
                } else {
                    confirm_pass.setCustomValidity("");
                }
            }
            password.onchange = validatePassword;
            confirm_pass.onkeyup = validatePassword;
        </script>

        <div id="myMessage" class="modal" >
            <div class="modal-content" style="text-align: center">

                <span class="close">&times;</span>
                <br/>

                <h2 style="color: green" ><s:property value="%{success}"/></h2>
                <h2 style="color: red" ><s:property value="%{fail}"/></h2>

            </div>
        </div>
        <div id="myFeedback" class="modal" >
            <div class="modal-content" style="text-align: center">

                <span class="close">&times;</span>
                <br/>
                <h2>Feedback</h2>

                <s:form action="Feedback" method="POST">
                    <s:textfield type="hidden" id="feedbackHotelID" name="hotelID" />
                    <s:textarea name="txtFeedback" cssStyle="width: 90%; height: 200px; resize: none" maxLength="500" placeholder="Maximum 500 char." ></s:textarea>
                    <s:submit value="Send Feedback"/>
                </s:form>

            </div>
        </div>

        <script>
            var modal = document.getElementById("myModal");
            var msg = document.getElementById("myMessage");
            var register = document.getElementById("myRegisterModal");
            var feedback = document.getElementById("myFeedback");
            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];
            var span2 = document.getElementsByClassName("close")[1];
            var span3 = document.getElementsByClassName("close")[2];
            var span4 = document.getElementsByClassName("close")[3];
            // When the user clicks the button, open the modal 
            function showForm() {
                modal.style.display = "block";
            }
            function showRegister() {
                register.style.display = "block";
                modal.style.display = "none";
            }
            function showFeedback(hotelID) {
                feedback.style.display = "block";
                document.getElementById("feedbackHotelID").value = hotelID;
            }


            // When the user clicks on <span> (x), close the modal
            span.onclick = function () {
                modal.style.display = "none";
            };
            span2.onclick = function () {
                register.style.display = "none";
            };
            span3.onclick = function () {
                msg.style.display = "none";
            };
            span4.onclick = function () {
                feedback.style.display = "none";
            }

            // When the user clicks anywhere outside of the modal, close it
            <s:if test="%{invalid != null}">
            modal.style.display = "block";
            </s:if>


            <s:if test = "%{success != null || fail != null}" >
            msg.style.display = "block";
            </s:if>
        </script>

        <!--nav-->
        <script>
            window.onscroll = function () {
                doSticky();
            };

            // Get the navbar
            var navbar = document.getElementById("navbar");

            // Get the offset position of the navbar
            var sticky = navbar.offsetTop;

            // Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
            function doSticky() {
                if (window.pageYOffset >= sticky) {
                    navbar.classList.add("sticky");
                } else {
                    navbar.classList.remove("sticky");
                }
            }
        </script>

        <script>
            /* When the user clicks on the button, 
             toggle between hiding and showing the dropdown content */
            function myFunction1() {
                document.getElementById("myDropdown1").classList.toggle("show");
                document.getElementById("myDropdown2").classList.remove("show");

            }
            function myFunction2() {
                document.getElementById("myDropdown2").classList.toggle("show");
                document.getElementById("myDropdown1").classList.remove("show");

            }
            function myFunction3() {

                document.getElementById("myDropdown1").classList.remove("show");
                document.getElementById("myDropdown2").classList.remove("show");
            }

            // Close the dropdown if the user clicks outside of it
            window.onclick = function (e) {
                if (!e.target.matches('.dropbtn')) {
                    var myDropdown1 = document.getElementById("myDropdown1");
                    var myDropdown2 = document.getElementById("myDropdown2");

                    if (myDropdown1.classList.contains('show')) {
                        myDropdown1.classList.remove('show');
                    }
                    if (myDropdown2.classList.contains('show')) {
                        myDropdown2.classList.remove('show');
                    }

                }
                if (e.target === modal || e.target === msg) {
                    modal.style.display = "none";
                    msg.style.display = "none";
                }
            }

        </script>
    </body>
</html>
