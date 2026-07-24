# Lab 1: Git Setup and Basic Commands

## Objectives

- Familiar with Git commands like `git init`, `git status`, `git add`, `git commit`, `git push`, and `git pull`.

## In this hands-on lab, you will learn how to:

- Setup your machine with Git Configuration
- Integrate notepad++.exe to Git and make it a default editor
- Add a file to source code repository

## Prerequisites

- Install Git Bash client in your machine

> **Notes:** Please follow the below steps for creating a free account in GitHub.
> Do not use cognizant credentials to login to GitHub.

**Estimated time to complete this lab: 30 minutes.**

---

## Step 1: Setup Your Machine with Git Configuration

To create a new repository, signup with GitLab and register your credentials.
Login to GitLab and create a "GitDemo" project.

### 1. Check if Git client is installed properly

Open Git Bash shell and execute:

```bash
git --version
```

If output shows Git with its version information, that indicates Git Client is installed properly.

### 2. Configure user level configuration (user ID and email ID)

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### 3. Verify the configuration is properly set

```bash
git config --list
```

---

## Step 2: Integrate notepad++.exe to Git and Make it a Default Editor

### 1. Check if notepad++.exe is accessible from Git Bash

```bash
notepad++
```

If Git Bash cannot recognize the `notepad++` command, it means notepad++.exe is not added to the environment path variable.

**To add notepad++.exe to the environment variable:**
- Go to **Control Panel → System → Advanced System Settings**
- Go to **Advanced tab → Environment Variables**
- Add the path of notepad++.exe (e.g., `C:\Program Files\Notepad++`) to the **Path** user variable by clicking **Edit**

### 2. Reopen Git Bash and verify

Exit Git Bash shell, reopen it, and execute:

```bash
notepad++
```

Now notepad++ should open from Git Bash shell.

### 3. Create an alias command for notepad++.exe

```bash
alias npp='notepad++'
```

It will open notepad++ from bash shell. Create a user profile by adding the following line in notepad++:

```bash
alias npp='notepad++'
```

### 4. Configure notepad++ as the default Git editor

```bash
git config --global core.editor "'C:/Program Files/Notepad++/notepad++.exe' -multiInst -notabbar -nosession -noPlugin"
```

### 5. Verify notepad++ is the default editor

```bash
git config -e --global
```

> Here the `-e` option implies editor.

This will open the entire global configuration in notepad++, showing:

```ini
[user]
    name = Your Name
    email = your.email@example.com
[core]
    editor = 'C:/Program Files/Notepad++/notepad++.exe' -multiInst -notabbar -nosession -noPlugin
```

---

## Step 3: Add a File to Source Code Repository

### 1. Create a new project "GitDemo"

Open Git Bash shell and execute:

```bash
mkdir GitDemo
cd GitDemo
git init
```

### 2. Verify the Git repository initialization

Git Bash initializes the "GitDemo" repository. To verify, execute:

```bash
ls -la
```

This will display all the hidden files in the Git **working directory**, including the `.git` folder.

### 3. Create a file "welcome.txt" and add content

```bash
echo "Welcome to Git Demo" > welcome.txt
```

### 4. Verify the file "welcome.txt" is created

```bash
ls
```

### 5. Verify the content of the file

```bash
cat welcome.txt
```

### 6. Check the Git status

```bash
git status
```

Now the file `welcome.txt` is available in the Git **working directory** (shown as untracked).

### 7. Stage the file to be tracked by Git

```bash
git add welcome.txt
```

### 8. Commit with multi-line comments using the default editor

```bash
git commit
```

Notepad++ editor will open. Add your multi-line commit message, save, and close the editor.

Or commit with an inline message:

```bash
git commit -m "Add welcome.txt with initial content"
```

### 9. Verify local and Working Directory are in sync

```bash
git status
```

`welcome.txt` is now added to the local repository. Status should show:
```
On branch master
nothing to commit, working tree clean
```

### 10. Create a remote repository "GitDemo" on GitLab

Signup with GitLab and create a remote repository named **GitDemo**.

### 11. Pull the remote repository

```bash
git pull origin master
```

### 12. Push the local repository to remote

```bash
git push origin master
```

---

## Summary of Commands Used

| Command | Description |
|---------|-------------|
| `git --version` | Check Git installation |
| `git config --global user.name` | Set global username |
| `git config --global user.email` | Set global email |
| `git config --list` | List all configurations |
| `git init` | Initialize a new repository |
| `git status` | Check working directory status |
| `git add <file>` | Stage a file |
| `git commit -m "message"` | Commit staged changes |
| `git push origin master` | Push to remote repository |
| `git pull origin master` | Pull from remote repository |
