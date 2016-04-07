<%@ page language="java" pageEncoding="utf-8"%>
	<s:iterator value="#request._form.formValues">
	   			<tr>
	   				<td class="form-feild"><span>${formElement.name}</span>
	   				</td>
	   				<td>
	   				${value}
	   				</td>
	   			</tr>
	</s:iterator>