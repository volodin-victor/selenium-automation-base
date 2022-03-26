
### HOW TO PUSH YOUR CHANGES TO THE GITHUB

---

1. Cut your own branch from main locally:<p>`git checkout -b name_of_your_branch`
and create remote copy <p>`git push --set-upstream origin name_of_your_branch`
The name of your local branch must match the name of your remote branch. Example (You can see in terminal): * [new branch] nik -> nik
2. Type git branch to verify that you created the branch (your currently branch will appear in green)
3. Make your changes
4. Run `git status` - your changes will appear in red
5. Add **ALL** your changes to staging by typing `git add`. OR to add some of the changes,
instead `git add`. you can assign path to the file you want to commit after adding Example:<p>```git add /Users/galina/IdeaProjects/ask/src/test/resources/features/1.ObjectsPropertiesMethods/firstTest.feature```
6. Type `git status` to check that you added all the changes (they will be shown in green)
7. To add your changes from stage to your branch: <p>```git commit -m "[ADDED] your_message_explaining_what_you_modified"``` <p>*Use different labels [ADDED] [CHANGED] [IMPROVED] [FIXED] [DELETED] to better understand what you did on branch*
8. Switch to `main` to update code on main branch: <p>```git checkout main``` And then: ```git pull```
9. Switch to your branch again: <p>`git checkout name_of_your_branch`
10. Type `git merge` main to make sure there won't be any conflicts in your future PR.
11. If there is no conflicts, you can push to github. Being on your branch, type `git push`
12. Once you collected all the changes on your remote branch, you can go ahead and create Pull request in GitHub.
13. Repeat steps 3-12 for each complete task