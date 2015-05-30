package commands

class foo {
    @Usage("make a foo")
    @Command
    public Object main() {
        return "bar!"
    }
}
