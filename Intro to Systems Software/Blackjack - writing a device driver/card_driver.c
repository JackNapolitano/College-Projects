#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <asm/uaccess.h>
#include <linux/random.h>


static ssize_t card_read(struct file * file, char * buf, 
			  size_t count, loff_t *ppos)
{
	int deck[52] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	unsigned char random_number;
	get_random_bytes(&random_number, sizeof(char));
	random_number = (random_number%52) //we are creating a random number from 0 to 51
	//while makes sure that we dont send the same card twice
	while(deck[random_number] != -1)
	{
		get_random_bytes(&random_number, sizeof(char));
		random_number = (random_number%52) //we are creating a random number from 0 to 51
	}
	deck[random_number] = 0;
	if (count < 1)
		return -EINVAL;
	
	if (*ppos != 0)
		return 0;

	
	if (copy_to_user(buf, &random_number, sizeof(ushort)/2))
		return -EINVAL;
	
	*ppos = 1;
	return 1;
}

/*
 * The only file operation we care about is read.
 */

static const struct file_operations card_fops = {
	.owner		= THIS_MODULE,
	.read		= card_read,
};

static struct miscdevice card_driver = {
	
	MISC_DYNAMIC_MINOR,
	
	"card",
	
	&card_fops
};

static int __init
card_init(void)
{
	int ret;

	ret = misc_register(&card_driver);
	if (ret)
		printk(KERN_ERR
		       "Unable to register \"card_driver\" misc device\n");

	return ret;
}

module_init(card_init);

static void __exit
card_exit(void)
{
	misc_deregister(&card_driver);
}

module_exit(card_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("John Napolitano <jon18@pitt.edu>"); //modified from hello_dev
MODULE_DESCRIPTION("\"card_driver\" minimal module");
MODULE_VERSION("dev");
