

#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <linux/random.h>
#include <asm/uaccess.h>



static ssize_t dice_read(struct file * file, char * buf, size_t count, loff_t *ppos) 
{

	if(count<1)
	{
		return -EINVAL;
	}

	if(*ppos != 0)
	{
		return 0;
	}

	unsigned char data;
	get_random_bytes(&data, sizeof(char));

	data = (data %52) + 1;

	if (copy_to_user(buf, &data, sizeof(ushort)/2))
	{
		return -EINVAL;
	}

	*ppos = 1;

	return 1;

}


static const struct file_operations dice_fops = 
{
	.owner		= THIS_MODULE,
	.read		= dice_read,
};

static struct miscdevice dice_driver = 
{
	
	MISC_DYNAMIC_MINOR,
	
	"dice",
	&dice_fops
};

static int __init
dice_init(void)
{
	int ret;
	ret = misc_register(&dice_driver);
	if (ret)
		printk(KERN_ERR "Unable to register \"dice_driver\" misc device\n");
	return ret;
}

module_init(dice_init);

static void __exit
dice_exit(void)
{
	misc_deregister(&dice_driver);
}

module_exit(dice_exit);
