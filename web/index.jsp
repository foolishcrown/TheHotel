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
        <title>Sang Sảng Hotel Shop (SSHS)</title>
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
        <h3 style="background-color: black; color: white; text-align: center">Dù ai đi ngược về xuôi, muốn đi khách sạn phải tìm web tui !</h3>
        <div class="navbar" id="navbar">
            <a href="'">Home</a>
            <div class="dropdown">
                <button class="dropbtn" onclick="myFunction1()">Search by Name
                    <i class="fa fa-caret-down"></i>
                </button>
                <div class="dropdown-content " id="myDropdown1">
                    <s:form action="SearchHotel" theme="simple" method="POST">
                        <a>
                            <s:textfield title="Hotel name" cssClass="dropbtn" style="background-color: white; color: black" type="text" name="txtSearch"/>
                        </a> 
                        <a><button class="dropbtn" style="background-color: green">Search</button></a>
                    </s:form>
                </div>
            </div> 
            <div class="dropdown">
                <button class="dropbtn" onclick="myFunction2()">Search by Date
                    <i class="fa fa-caret-down"></i>
                </button>
                <div class="dropdown-content " id="myDropdown2" >
                    <s:form action="SearchHotel"  method="POST">
                        <s:textfield  cssClass="dropbtn" onkeydown="return false" label="Check In" type="date" id="dayFrom"  name="checkin" required=""/>
                        <s:textfield cssClass="dropbtn" onkeydown="return false" label="Check Out" type="date" id="dayTo"   name="checkout" required=""/> 
                        <s:if test="%{#session.AREAS != null}">
                            <s:select cssClass="dropbtn" headerValue="All" headerKey="-1" label="Area" list="%{#session.AREAS}" name="areaID" listKey="areaID" listValue="areaName" />
                        </s:if>
                        <s:textfield cssClass="dropbtn" id="numRoom" name="numOfRoom" label="Room" type="number" required="" min="1" max="100" />
                        <s:submit cssClass="dropbtn" cssStyle="background-color: green" value="Search" />
                    </s:form>
                </div>
            </div> 

            <!--script for date-->
            <script>
                var dayFrom = document.getElementById("dayFrom")
                        , dayTo = document.getElementById("dayTo");
                var numRoom = document.getElementById("numRoom");
                dayFrom.min = new Date().toISOString().slice(0, 10);
                dayTo.min = new Date().toISOString().slice(0, 10);
                if (numRoom.value === "") {
                    numRoom.value = 1;
                }
                if (dayFrom.value === "") {
                    dayFrom.value = new Date().toISOString().slice(0, 10);
                }

                const tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                if (dayTo.value === "") {
                    dayTo.value = tomorrow.toISOString().slice(0, 10);
                }


                function comparedDay() {
                    var dF = new Date(dayFrom.value);
                    var dT = new Date(dayTo.value);
                    var numDay = parseInt(dT - dF) / (24 * 3600 * 1000);

                    dayFrom.max = dT.toISOString().slice(0, 10);
                    dayTo.min = dF.toISOString().slice(0, 10);

                    if (dF.getTime() === dT.getTime()) {
                        dayTo.setCustomValidity("Checkin and Checkout must not be same day.");
                    } else {
                        dayTo.setCustomValidity("");
                    }
//                    var originPrice = document.getElementById("originPrice").value;
//                    if (numDay < 0) {
//                        numDay = 0;
//                    }
//                    document.getElementById("price").value = numDay * originPrice;
                }

                dayFrom.onchange = comparedDay;
                dayTo.onchange = comparedDay;
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

        <s:if test="%{listHotel != null}" >
            <s:iterator value="listHotel" status="counter">
                <div class="container-hotel" onmouseover="this.style.backgroundColor = 'darkgray'" onmouseout="this.style.backgroundColor = 'black'" style="width: 100%; height: 250px; background-color: black; position: relative">
                    <div class="img-hotel" style="width: 20%; height: 100%; float: left; margin-right: 2.5%">
                        <img src="images/<s:property value="photo"/>" style="width: 100%; height: 100%"/>
                    </div>
                    <div class="context-hotel"  style="color: white;">
                        <h1 style="color: steelblue"><s:property value="name" /></h1>
                        <p>Area: <s:iterator value="%{#session.AREAS}">
                                <s:if test="areaID == areaId" ><s:property value="areaName" /></s:if>
                            </s:iterator> </p>
                        <p>Address: <a href="#" style="color: slateblue"><s:property value="address"/></a></p>
                        <p><s:property value="description" /></p>
                        <s:url action="ViewDetail" id="ViewLink">
                            <s:param name="hotelID" value="%{id}" />
                            <s:param name="checkin" value="%{checkin}" />
                            <s:param name="checkout" value="%{checkout}" />
                        </s:url>
                        <s:a href="%{ViewLink}"><button style="position: absolute; width: 100px; right: 0px; bottom: -3.2%;" type="button">View Detail</button></s:a>
                        </div>
                    </div>
            </s:iterator>
        </s:if>

        <!--detail-->
        <s:if test="%{dto != null}">
            <div class="container-hotel"  style="width: 100%; height: 250px; background-color: black; position: relative">
                <div class="img-hotel" style="width: 20%; height: 100%; float: left; margin-right: 2.5%">
                    <img src="images/<s:property value="dto.photo"/>" style="width: 100%; height: 100%"/>
                </div>
                <div class="context-hotel"  style="color: white;">
                    <h1 style="color: steelblue"><s:property value="dto.name" /></h1>
                    <p>*Area: <s:iterator value="%{#session.AREAS}">
                            <s:if test="areaID == dto.areaId" ><s:property value="areaName" /></s:if>
                        </s:iterator> </p>
                    <p>*Address: <a href="#" style="color: slateblue"><s:property value="dto.address"/></a>  *Phone:<s:property value="dto.phone"/> </p>
                    <p><s:property value="dto.description" /></p>
                </div>
            </div>   
            <s:if test="%{listType != null}">
                <s:form action="ViewDetail" method="POST">
                    <s:submit theme="simple" cssStyle="display: none;" id="updateBtn" />
                    <s:textfield type="hidden" name="hotelID" value="%{dto.id}"/>
                    <s:textfield  cssClass="dropbtn" onkeydown="return false" label="Check In" type="date" id="checkin" value="%{checkin}" name="checkin" required=""/>
                    <s:textfield cssClass="dropbtn" onkeydown="return false" label="Check Out" type="date" id="checkout" value="%{checkout}"  name="checkout" required=""/> 
                </s:form>
                <div class="container-hotel" s style="width: 100%; background-color: black; position: relative">
                    <table border="1">

                        <tbody>
                            <s:iterator value="%{listType}" >

                                <s:form theme="simple" action="AddCart" method="POST">
                                    <s:textfield type="hidden" name="typeID" value="%{id}" />
                                    <s:textfield type="hidden" name="checkin" value="%{checkin}"/>
                                    <s:textfield type="hidden" name="checkout" value="%{checkout}"/>
                                    <s:textfield type="hidden" name="hotelID" value="%{dto.id}" />
                                    <tr>
                                        <td><s:label value="%{name} Room:"/></td>
                                        <td><s:label cssStyle="color: red" value="%{availableAmount} left"/> </td>
                                        <td><s:if test="%{#session.ACCOUNT != null && #session.ACCOUNT.role != 0}"><s:submit cssStyle="height: 50px;" value="Add Cart" theme="simple"/></s:if><s:else><input type="button" style="height: 50px;" onclick="showForm()" value="Add Cart"/></s:else></td>
                                        </tr>
                                </s:form>

                            </s:iterator>
                        </tbody>
                    </table>
                </div>
            </s:if>   
            <br/>
            <br/>
            <br/>
            <h2>Feedback</h2>
            <br/>
            <s:if test="%{listFeedback != null}">
                <s:iterator value="%{listFeedback}">
                    <div style="width: 100%; height: 250px; background-color: #f1f1f1; position: relative; padding-top: 2%; border: green; border-style: solid; padding-left: 2%">
                        <div style="width: 20%; height: 100%; float: left; margin-right: 2.5%">
                            <h3><s:property value="%{email}"/><br/></h3>
                            <a href="#"><s:property value="%{date}"/> </a>
                        </div>
                        <div>
                            <textarea  readonly="" value="" style="width: 50%; height: 200px; resize: none"><s:property value="%{content}"/></textarea>
                        </div>

                    </div>
                </s:iterator>
            </s:if>



            <!--script for date-->
            <script>
                var checkin = document.getElementById("checkin")
                        , checkout = document.getElementById("checkout");

                checkin.min = new Date().toISOString().slice(0, 10);
                checkout.min = new Date().toISOString().slice(0, 10);

                if (checkin.value === "") {
                    checkin.value = dayFrom.value;
                }

                if (checkout.value === "") {
                    checkout.value = dayTo.value;
                }


                function comparedDays() {
                    var chkIn = new Date(checkin.value);
                    var chkOut = new Date(checkout.value);
                    var diffDay = parseInt(chkIn - chkOut) / (24 * 3600 * 1000);

                    checkin.max = chkOut.toISOString().slice(0, 10);
                    checkout.min = chkIn.toISOString().slice(0, 10);

                    if (chkIn.getTime() === chkOut.getTime()) {
                        checkout.setCustomValidity("Checkin and Checkout must not be same day.");
                    } else {
                        checkout.setCustomValidity("");
                    }
                    document.getElementById("updateBtn").click();
                }

                checkin.onchange = comparedDays;
                checkout.onchange = comparedDays;
            </script>
        </s:if>



        <!--LoginModal-->
        <div id="myModal" class="modal">
            <div class="modal-content">

                <span class="close">&times;</span>
                <br/>   
                <h3>Login</h3>
                <h5 style="color: red"><s:property value="%{invalid}" /></h5>
                <s:form cssClass="form" theme="simple" action="Login" method="POST">
                    <s:textfield  type="email" theme="simple" placeholder="Email" name="email"/><br/>
                    <s:password  type="password" theme="simple" placeholder="Password" name="password"/><br/>
                    <button type="submit" value="Login">Login</button>
                </s:form>
                <button style="background-color: red" onclick="showRegister()">Register Account</button>
            </div>
        </div>

        <!--RegisterModal-->
        <div id="myRegisterModal" class="modal">
            <div class="modal-content">

                <span class="close">&times;</span>
                <br/>   
                <h3>Register Account</h3>
                <h5 style="color: red"><s:property value="%{invalid}" /></h5>
                <s:form theme="simple" cssClass="form" action="Register" method="POST">
                    <s:textfield type="email" required="" placeholder="Email" name="email"/><br/>
                    <s:password id="pass" type="password" required="" placeholder="Password" name="password"/><br/>
                    <s:password id="confirm-pass" type="password" required="" placeholder="Confirm" name="confirm"/><br/>
                    <s:textfield type="text" placeholder="Address" required="" name="address"/><br/>
                    <s:textfield type="tel" pattern="[0-9]{10}" required="" placeholder="Phone (10 number)" name="phone"/>
                    <button type="submit">Register</button>
                </s:form>
            </div>
        </div>
        <script>
            var password = document.getElementById("pass"), confirm = document.getElementById("confirm-pass");
            function validatePassword() {
                if (password.value !== confirm.value) {
                    confirm.setCustomValidity("Confirm must be match with Password!");
                } else {
                    confirm.setCustomValidity("");
                }
            }
            password.onchange = validatePassword;
            confirm.onkeyup = validatePassword;
        </script>

        <div id="myMessage" class="modal" >
            <div class="modal-content" style="text-align: center">

                <span class="close">&times;</span>
                <br/>

                <h2 style="color: green" ><s:property value="%{success}"/></h2>
                <h2 style="color: red" ><s:property value="%{fail}"/></h2>


            </div>
        </div>


        <script>
            var modal = document.getElementById("myModal");
            var msg = document.getElementById("myMessage");
            var register = document.getElementById("myRegisterModal");
            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];
            var span2 = document.getElementsByClassName("close")[1];
            var span3 = document.getElementsByClassName("close")[2];
            // When the user clicks the button, open the modal 
            function showForm() {
                modal.style.display = "block";
            }
            function showRegister() {
                register.style.display = "block";
                modal.style.display = "none";
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
                if (e.target === modal || e.target === msg || e.target === feedback) {
                    modal.style.display = "none";
                    msg.style.display = "none";
                    feedback.style.display = "none";
                }
            }

        </script>
    </body>
</html>
