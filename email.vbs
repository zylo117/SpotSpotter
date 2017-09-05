receiptions = "Jiahao.Zhang@o-film.com; Jiamin.Yang@o-film.com"
Subject = "hello"

body = "Here are the latest information to you"
Attachments = Array(".\email_content.txt")

Dim xOutLook
Dim xMail
On Error Resume Next
Set xOutLook = GetObject(, "Outlook.Application")

If xOutLook Is Nothing Then
 Set xOutLook = CreateObject("Outlook.Application")
End If

Set fso = CreateObject("Scripting.FileSystemObject")
Set MyFile = fso.OpenTextFile(".\email_content.txt", 1, False)
 
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