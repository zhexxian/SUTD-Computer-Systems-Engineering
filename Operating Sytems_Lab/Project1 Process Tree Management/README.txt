/* Programming Assignment 1 
* Author : Zhang Zhexian 
* ID : 1001214 
* Date : 13/03/2016 */


CONTENTS OF THIS FILE
---------------------
   
* Introduction
* Purpose
* Requirements
* Compilation
* Design


INTRODUCTION
------------

The process tree management program is an automated program for easy
process execution. It accepts commands of how user programs should be
executed in a text file, and executes them in the specified sequence.


PURPOSE
-------

* Automation

Human beings are not known to be good at performing repeatitive, linear
tasks, especially when it involved boring comman line interface, and
when the number of tasks becomes overwhelming.

That's why we have computers!

With the process tree management program, long list of command line 
instructions can be easily executed in only one step. 

* Convenience

Another problem solved -- the headache when a process involves copying 
and pasting contents in multiple text files. The process tree management 
program is able to manage the file system too.

* Efficiency

The process tree management program is smart enought to detect the 
processes that can run in parallel, thus allowing multitasking and a
significant increase in efficiency.


REQUIREMENT
-----------

To use the program, a computer running on the UNIX operating system
is required. (Example of common UNIX operating system includes the 
iOS and Linux systems; Windows users may install UNIX virtual machine
to use the program.)

Also, Java Runtime Environment (JRE) needs to be installed.
URL: http://www.oracle.com/technetwork/java/javase/downloads/index.html


COMPILATION
-----------

* Step 1: Prepare input file

If you already have the input file (with format similar to the following) 
ready, go to Step 2.

Input format:
<program>:<child nodes>:<input>:<output>	
Example:
echo hi there:1:stdin:echo-out.txt

It is easy to prepare the input file: open a text editor, on each line, in
order enter:
	a) the command line instruction with arguments (seperated by 
	space);
	b) child nodes (line number of the child command);
	c) input: either the file name or 'stdin';
	d) output: either the file name or 'stdout'.

* Step 2: Command line interface

Open the shell/batch, migrate to the working directory with the program
and the input file.

Learn more about command line interface: 
https://www.google.com.sg/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=command%20line%20interface%20unix

* Step 3: Run the program

First, compile the java program

Example:  javac ProcessMgt.java

Then, run the program with the input file

Example: java ProcessMgt input.txt


DESIGN
------


Process Management Procesure:

				|-------------------------|             |--------------|
                |   Parse the Text File   |<---input--->|   input.txt  |
                |-------------------------|             |--------------|
                           /                                  |
                          /                                   |
                         /                                    |
                        /                                     |
                     extract                                  |
                      /                                    Automation
                     /                                        |
                    /                                         | 
                   v                                          |
    |---------------------------|                             |-----------|
    |     Internode Relation    |						      |           |
    |	  (list of parents)     |--execute command in order-->| output    |
    |---------------------------|                             |-----------|
   