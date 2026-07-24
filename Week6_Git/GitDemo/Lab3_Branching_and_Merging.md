# Lab 3: Branching and Merging

## Objectives

- Explain branching and merging
- Explain about creating a branch request in GitLab
- Explain about creating a merge request in GitLab

## In this hands-on lab, you will learn how to:

- Construct a branch, do some changes in the branch, and merge it with master (or trunk)

## Prerequisites

- Setting up Git environment with P4Merge tool for Windows

> **Notes:** Please follow the below steps for creating a free account in GitHub.
> Do not use cognizant credentials to login to GitHub.

**Estimated time to complete this lab: 30 minutes.**

---

## Step-by-Step Instructions

### BRANCHING

#### 1. Create a new branch "GitNewBranch"

```bash
git branch GitNewBranch
```

#### 2. List all local and remote branches

```bash
git branch -a
```

Observe the `*` mark which denotes the **currently active branch** (should be on `master` at this point).

Expected output:
```
* master
  GitNewBranch
  remotes/origin/master
```

#### 3. Switch to the newly created branch

```bash
git checkout GitNewBranch
```

Or create and switch in one command:

```bash
git checkout -b GitNewBranch
```

Verify you are on the new branch:

```bash
git branch
```

Output:
```
* GitNewBranch
  master
```

#### 4. Add files with content to the branch

```bash
echo "This file is created in GitNewBranch" > branch_feature.txt
echo "Feature implementation in progress" >> branch_feature.txt
```

#### 5. Commit the changes to the branch

```bash
git add branch_feature.txt
git commit -m "Add branch_feature.txt in GitNewBranch"
```

#### 6. Check the status

```bash
git status
```

Expected output:
```
On branch GitNewBranch
nothing to commit, working tree clean
```

---

### MERGING

#### 1. Switch back to master

```bash
git checkout master
```

#### 2. List differences between master and branch (command line)

```bash
git diff master..GitNewBranch
```

This shows differences in the command line interface.

#### 3. Visual differences using P4Merge tool

First, configure P4Merge as the diff tool:

```bash
git config --global diff.tool p4merge
git config --global difftool.p4merge.path "C:/Program Files/Perforce/p4merge.exe"
```

Then launch the visual diff:

```bash
git difftool master..GitNewBranch
```

P4Merge will open and display a visual side-by-side comparison.

#### 4. Merge the branch into master

```bash
git merge GitNewBranch
```

Expected output (fast-forward merge):
```
Updating abc1234..def5678
Fast-forward
 branch_feature.txt | 2 ++
 1 file changed, 2 insertions(+)
 create mode 100644 branch_feature.txt
```

#### 5. Observe the log after merging

```bash
git log --oneline --graph --decorate
```

This shows a visual graph of the commit history, similar to:
```
* def5678 (HEAD -> master, GitNewBranch) Add branch_feature.txt in GitNewBranch
* abc1234 Add welcome.txt with initial content
```

#### 6. Delete the branch after merging

```bash
git branch -d GitNewBranch
```

Verify the branch is deleted:

```bash
git branch -a
```

Expected output:
```
* master
  remotes/origin/master
```

---

## Key Concepts

| Concept | Description |
|---------|-------------|
| `git branch <name>` | Create a new branch |
| `git checkout <branch>` | Switch to a branch |
| `git checkout -b <branch>` | Create and switch to a new branch |
| `git branch -a` | List all local and remote branches |
| `git diff <branch1>..<branch2>` | Show differences between branches |
| `git difftool` | Open visual diff tool |
| `git merge <branch>` | Merge a branch into the current branch |
| `git branch -d <branch>` | Delete a branch (safe — only if merged) |
| `git log --oneline --graph --decorate` | Visual commit history graph |

---

## Branch Workflow Diagram

```
master:         A---B
                     \
GitNewBranch:         C---D
                           \
After merge:    A---B-------E  (E merges D into master)
```
