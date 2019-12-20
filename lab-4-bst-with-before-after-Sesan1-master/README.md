# Lab: BST with getBefore and getAfter methods

This lab is a stepping stone for the Segment Intersection Project.
You will complete the implementation of a binary search tree (without balancing) in this lab.
In prior assignments you've seen how to implement the three Set ADT operations for
binary search trees: `search` (aka. `find`), `insert`, and `remove`. You've also seen
how to keep track of the height of each node. In this lab you will 

* Refactor those operations into the special `BinarySearchTree` class needed for the
    Segment Intersection Project, and
* Implement the `getBefore` and `getAfter` methods, which are also needed
    for Segment Intersection.
    
The `getBefore` and `getAfter` methods will require that every node maintain
a `parent` field that points to the parent of the node. As usual, you may
refer to any material that you find helpful (textbook, prior assignments, lectures,
the internet, etc.). In particular, the lecture notes contains a suggestion for
how to implement the `getAfter` method (in the notes call it `after`).