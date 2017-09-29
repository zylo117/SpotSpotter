receiptions = "123@abc.com"
Subject = "HelloWorld"
currentpath= createobject("Scripting.FileSystemObject").GetFile(Wscript.ScriptFullName).ParentFolder.Path

'body = "Here are the latest information to you"
'Attachments = Array(currentpath & "\" & "email_content.txt")

Dim xOutLook
Dim xMail
On Error Resume Next
Set xOutLook = GetObject(, "Outlook.Application")

If xOutLook Is Nothing Then
Set xOutLook = CreateObject("Outlook.Application")
End If

Set fso = CreateObject("Scripting.FileSystemObject")
Set MyFile = fso.OpenTextFile(currentpath & "\" & "email_content.txt", 1, False)
 
Do While MyFile.AtEndOfLine <> True
    UserName = MyFile.ReadLine
    bb = bb & Chr(11) & UserName & Chr(11)
Loop
MyFile.Close

Set xMail = xOutLook.CreateItem(olMailItem)
With xMail
 .Display
 Dim signature
 signature = .HTMLBody
 .To = receiptions
 .Subject = Subject
.Importance = olImportanceHigh

 Dim xDoc
 'Dim bb
 Set xDoc = xMail.Application.ActiveInspector.WordEditor

 .HTMLBody = bb
Debug.Print bb
 .send

End With