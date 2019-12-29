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
        <title>Booking Cart</title>
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


            <!--script for date-->


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

        <script type="text/javascript">

            function increase(btnID)
            {
                var txt = document.getElementById(btnID);
                var txtValue = txt.value - -1;
                if (txtValue > txt.max)
                    txtValue = txt.max;
                if (txtValue < txt.min)
                    txtValue = txt.min;
                txt.value = txtValue;
                document.getElementById("updateBtn").click();
            }
            function decrease(btnID)
            {
                var txt = document.getElementById(btnID);
                var txtValue = txt.value - 1;
                if (txtValue <= 0) {
                    txtValue = 1;
                }
                txt.value = txtValue;
                document.getElementById("updateBtn").click();
            }
            function contrainstTheValue(textBox)
            {
                var value = parseInt(textBox.value);
                // console.log(textBox.max);
                if (value > textBox.max)
                    value = textBox.max;
                if (value < textBox.min)
                    value = textBox.min;
                console.log("value" + value);
                textBox.value = value;

                if (textBox.value === value) {
                    document.getElementById("updateBtn").click();
                }

            }
        </script>

        <s:if test="%{#session.CART != null}">
            <s:if test="%{#session.CART.getCartDetail().size() > 0}">
                <s:form action="UpdateCart" method="post">
                    <div style="overflow-x: auto">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>NO.</th>
                                    <th>Hotel Name</th>
                                    <th>Room Type</th>
                                    <th style="width: 12%;">Amount</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                    <th>Numdays</th>
                                    <th>Remove</th>
                                </tr>
                            </thead>
                            <tbody>
                            <button id="updateBtn" style="display: none" />
                            <s:iterator value="%{#session.CART.getCartDetail()}" status="counter" var="dto">
                                <tr>
                                    <td>
                                        <s:property value="%{#counter.count}"/>
                                    </td>
                                    <td>
                                        <s:iterator value="%{#session.HOTELS}" var="hotelDTO">
                                            <s:if test="%{#dto.hotelID == #hotelDTO.id}">
                                                <s:property value="%{#hotelDTO.name}"/>
                                            </s:if>
                                        </s:iterator>
                                    </td>
                                    <td>
                                        <s:property value="%{name}"/>
                                        <s:textfield theme="simple" type="hidden" name="typeID" value="%{id}"/>
                                    </td>
                                    <td style="text-align: center"> 
                                        <a style="cursor: pointer; font-size: 30px" onclick="decrease('<s:property value="%{id}"/>');"><strong>-</strong></a>
                                        <s:textfield theme="simple" id="%{id}" onchange="document.getElementById('updateBtn').click();" onkeydown="contrainstTheValue(this);" type="number" name="amount" max="%{availableAmount}" min="1" value="%{amountCart}" />
                                        <a style="cursor: pointer;font-size: 25px" onclick="increase('<s:property value="%{id}"/>');"><strong>+</strong></a>
                                    </td>
                                    <td><s:property value="%{price}" /> &dstrok;</td>
                                    <td><s:property value="%{price * amountCart * numDays}"/> &dstrok;</td>
                                    <td>
                                        <s:property value="%{numDays}"/>
                                    </td>
                                    <td>
                                        <s:url id="LinkDelete" action="RemoveCart">
                                            <s:param name="typeID" value="%{id}"/>
                                        </s:url>
                                        <a href="#" onclick="confirmDelete(this, '<s:property value="%{LinkDelete}"/>');" style="text-decoration: none; font-size: 30px">
                                            <strong><span>&times;</span></strong>
                                        </a>
                                    </td>
                                    <s:textfield onkeydown="return false" onload="comparedDays('%{id}')" label="Check In" type="date" onchange="comparedDays('%{id}')" id="checkin%{id}" value="%{checkinCart}" name="checkin" required=""/>
                                    <s:textfield onkeydown="return false" onload="comparedDays('%{id}')"  label="Check Out" type="date" onchange="comparedDays('%{id}')" id="checkout%{id}" value="%{checkoutCart}"  name="checkout" required=""/>
                                </tr>

                            </s:iterator>
                            <script>
                                function confirmDelete(link, address) {
                                    var isDelete = confirm("Do you want to remove ?");
                                    if (isDelete === true) {
                                        link.href = address;
                                        link.onclick = "";
                                        link.click();
                                    }
                                }
                            </script>
                            <tr>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="3" style="text-align: right"><h3>BILL TOTAL:</h3></td>
                                <td><h4> <s:property value="%{#session.CART.getTotal() * (1 - #session.CART.discount)}"/> &dstrok;</h4></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </s:form>

                <s:form  action="ApplyDiscount" method="POST">
                    <div style="overflow-x: auto">
                        <table border="1">
                            <thead>
                                <tr style="text-align: center">
                                    <th style="width: 50%">Discount Code: <input required="" value="<s:property value="%{#session.CART.discountCode}"/>" name="discountCode" type="text" <s:if test="%{#session.CART.discount != 0}">disabled=""</s:if> /><button type="but">Apply</button></th>
                                        <th style="width: 50%; height: 100%"><a style="height: 100%" href="ConfirmMail" ><button style="height: 90%" type="button" >Verify Confirm</button></a>
                                        <a style="height: 100%" href="ConfirmCart" ><button style="height: 90%" type="button" >Normal Confirm</button></a></th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                </s:form>                
            </s:if>
            <s:else>
                Empty Cart
            </s:else>
        </s:if>
        <s:else>
            Cart Not Found!
        </s:else>





        <!--script for date-->
        <script>


            function comparedDays(textID) {
                var checkin = document.getElementById("checkin" + textID)
                        , checkout = document.getElementById("checkout" + textID);
                var chkIn = new Date(checkin.value);
                var chkOut = new Date(checkout.value);

                checkin.max = chkOut.toISOString().slice(0, 10);
                checkin.min = new Date().toISOString().slice(0, 10);
                checkout.min = chkIn.toISOString().slice(0, 10);

                if (chkIn.getTime() === chkOut.getTime()) {
                    checkout.setCustomValidity("Checkin and Checkout must not be same day.");
                } else {
                    checkout.setCustomValidity("");
                    document.getElementById("updateBtn").click();
                }

            }
        </script>


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
        <div id="myConfirm" class="modal" >
            <div class="modal-content" style="text-align: center">

                <span class="close">&times;</span>
                <br/>
                <h3><s:property value="%{confirmed}"/></h3>
                <s:form action="AcceptConfirm" method="POST" >
                    <s:textfield label="Code (6 num)" type="text" min="0" name="confirmCode"/>
                    <s:submit value="Confirm"/>
                </s:form>


            </div>
        </div>

        <script>
            var modal = document.getElementById("myModal");
            var msg = document.getElementById("myMessage");
            var register = document.getElementById("myRegisterModal");
            var confirmMail = document.getElementById("myConfirm");
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
            function showConfirm() {
                confirmMail.style.display = "block";
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
                confirmMail.style.display = "none";
            };


            // When the user clicks anywhere outside of the modal, close it
            <s:if test="%{invalid != null}">
            modal.style.display = "block";
            </s:if>


            <s:if test = "%{success != null || fail != null}" >
            msg.style.display = "block";
            </s:if>

            <s:if test="%{confirmed != null}">
                confirmMail.style.display = "block";
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
                if (e.target === modal || e.target === msg ) {
                    modal.style.display = "none";
                    msg.style.display = "none";
                }
            }

        </script>
    </body>
</html>
